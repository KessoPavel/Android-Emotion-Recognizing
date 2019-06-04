package com.kesso;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kesso.er.FaceFrameDrawer.Render.ErRender;
import com.kesso.er.FaceFrameDrawer.VIew.ErGLSurfaceView;
import com.kesso.er.Search.Input.BaseInput.IFrame;
import com.kesso.er.Search.Input.CameraInput.ErCamera.ErCamera;
import com.kesso.er.Search.Input.CameraInput.ICameraBaseInput;
import com.kesso.er.Search.Ouptut.BaseOutput.Face;
import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput;
import com.kesso.er.Search.Searcher.Searcher;
import com.kesso.er.Search.SearcherModule;
import com.kesso.mylibrary.Classifier;
import com.kesso.mylibrary.MClassifier;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IBaseOutput {
    private CameraBridgeViewBase cameraBridgeViewBase;
    private ErGLSurfaceView mGLSurfaceView;
    private ErRender mErRender;

    private int height;
    private int width;

    private TextView size;
    private TextView count;
    private SeekBar bsize;
    private SeekBar bcount;

    MClassifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        size = findViewById(R.id.size);
        count = findViewById(R.id.count);
        bsize = findViewById(R.id.bsize);
        bcount = findViewById(R.id.bcount);
        bsize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                size.setText("" + progress * 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bcount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mGLSurfaceView = new ErGLSurfaceView(this);
        mGLSurfaceView.setId(View.generateViewId());
        mGLSurfaceView.setZOrderOnTop(true);

        ConstraintLayout view = findViewById(R.id.glLiner);
        ConstraintSet constraintSet = new ConstraintSet();
        view.addView(mGLSurfaceView);
        constraintSet.clone(view);
        constraintSet.connect(mGLSurfaceView.getId(), ConstraintSet.TOP, view.getId(), ConstraintSet.TOP, 0);
        constraintSet.applyTo(view);

        mErRender = mGLSurfaceView.getErRender();

        cameraBridgeViewBase = findViewById(R.id.fd_activity_surface_view);
        SearcherModule searcherModule = new SearcherModule(this, cameraBridgeViewBase, this);
        searcherModule.init();
        searcherModule.open();

        ICameraBaseInput iCameraBaseInput = (ICameraBaseInput) searcherModule.getInput();
        iCameraBaseInput.setCurrentCamera(ErCamera.FRONT);
        ((Searcher)searcherModule.getSearcher()).setMinFaceSize((float) 0.2);

        try {
             classifier = MClassifier.create(this, MClassifier.Device.CPU, 1, MClassifier.Model.TFModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean t = false;
    @Override
    public void receive(IFrame frame, final List<Face> searchFaces) {
        List<float[]> faceFrames = new ArrayList<>();
        List<Mat> faces = new ArrayList<>();


        for (Face face : searchFaces){
            int x1 = face.getX1();
            int x2 = face.getX2();
            int y1 = face.getY1();
            int y2 = face.getY2();

            float cvHeight = frame.getData().height();
            float cvWidth = frame.getData().width();

            RunnableM runnableM = new RunnableM();
            runnableM.fr = frame.getData().clone();
            runnableM.x1 = x1;
            runnableM.x2 = x2;
            runnableM.y1 = y1;
            runnableM.y2 = y2;
            runnableM.x = (int) (x1 * (cvWidth / width));
            runnableM.y = (int) (y1 * (cvHeight / height));


            if (!t) {
                Thread thread = new Thread(runnableM);
                thread.start();
            }
            float gl_x1, gl_x2, gl_y1, gl_y2;


            gl_x1 = ((2 * (x1 / cvWidth)) - 1) * (cvWidth / width);
            gl_x2 = ((2 * (x2 / cvWidth)) - 1) * (cvWidth / width);
            gl_y1 = ((2 * (y1 / cvHeight)) - 1) * (cvHeight / height);
            gl_y2 = ((2 * (y2 / cvHeight)) - 1) * (cvHeight / height);


            faceFrames.add(new float[]{gl_x1, -gl_y1, gl_x2, -gl_y2, 0.01f});
            break;
        }

        mErRender.getFaceFrames().clear();
        mErRender.getFaceFrames().addAll(faceFrames);
        mGLSurfaceView.requestRender();
        frame.getData().release();
    }

    class RunnableM implements Runnable {
        public Mat fr;
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int x;
        public int y;

        @Override
        public void run() {
            try {
                t = true;
                Rect roi = new Rect(x1, y1, x2 - x1, y2 - y1);
                Mat crop = new Mat(fr, roi);
                Size sz = new Size(64, 64);
                Mat resize = new Mat();
                Imgproc.resize(crop, resize, sz);

                byte[] arr = new byte[64 * 64];
                resize.get(0, 0, arr);

                List<MClassifier.Recognition> c = classifier.recognizeImage(arr);
                String s = c.get(0).getTitle();

                String finalS = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(MainActivity.this, finalS, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, x1, y2);
                        toast.show();
                    }
                });
                //runOnUiThread(() -> Toast.makeText(MainActivity.this, finalS, Toast.LENGTH_SHORT).show(););
//            synchronized (this) {
//                try {
//                    this.wait(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
                t = false;
            }catch (Exception ignore){

            }
        }
    }
}