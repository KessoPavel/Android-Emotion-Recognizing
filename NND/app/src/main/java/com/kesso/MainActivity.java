package com.kesso;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kesso.er.core.detect.DetectorModule;
import com.kesso.er.core.detect.output.IDetectorOutput;
import com.kesso.er.core.face.IFace;
import com.kesso.er.core.frame.IBaseFrame;
import com.kesso.er.core.search.SearcherModule;
import com.kesso.er.openGLWrapper.render.ErRender;
import com.kesso.er.openGLWrapper.vIew.ErGLSurfaceView;
import com.kesso.mylibrary.EmotionClassifier;
import com.kesso.nnilib.Device;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IDetectorOutput {
    private CameraBridgeViewBase cameraBridgeViewBase;
    private ErGLSurfaceView mGLSurfaceView;
    private ErRender mErRender;

    private int height;
    private int width;

    EmotionClassifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGLSurfaceView = new ErGLSurfaceView(this);
        mGLSurfaceView.setId(View.generateViewId());
        mGLSurfaceView.setZOrderOnTop(true);

        cameraBridgeViewBase = findViewById(R.id.fd_activity_surface_view);
        ConstraintLayout view = findViewById(R.id.glLiner);
        ConstraintSet constraintSet = new ConstraintSet();
        view.addView(mGLSurfaceView);
        constraintSet.clone(view);
        constraintSet.connect(mGLSurfaceView.getId(), ConstraintSet.TOP, view.getId(), ConstraintSet.TOP, 0);
        constraintSet.applyTo(view);

        mErRender = mGLSurfaceView.getErRender();


        SearcherModule searcherModule = new SearcherModule(this, cameraBridgeViewBase);
        DetectorModule detectorModule = new DetectorModule(this, this, this, Device.CPU);
        detectorModule.init();
        searcherModule.setSearcherOutput(detectorModule.getInput());
        searcherModule.init();


        detectorModule.open();
        searcherModule.open();
    }

    @Override
    public void receive(IBaseFrame frame) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (IFace face : frame.getFaces()){
                    Toast toast = Toast.makeText(MainActivity.this, face.getEmotion(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, face.getLeftTopX(), face.getLeftTopY());
                    toast.show();
                }
            }
        });
    }

//    boolean t = false;
//    @Override
//    public void receive(IFrame frame, final List<? extends IFace> searchFaces) {
//        List<float[]> faceFrames = new ArrayList<>();
//        List<Mat> faces = new ArrayList<>();
//
//
//        for (IFace face : searchFaces){
//            int x1 = face.getX1();
//            int x2 = face.getX2();
//            int y1 = face.getY1();
//            int y2 = face.getY2();
//
//            float cvHeight = frame.getData().height();
//            float cvWidth = frame.getData().width();
//
//            RunnableM runnableM = new RunnableM();
//            runnableM.fr = frame.getData().clone();
//            runnableM.x1 = x1;
//            runnableM.x2 = x2;
//            runnableM.y1 = y1;
//            runnableM.y2 = y2;
//            runnableM.x = (int) (x1 * (cvWidth / width));
//            runnableM.y = (int) (y1 * (cvHeight / height));
//
//
//            if (!t) {
//                Thread thread = new Thread(runnableM);
//                thread.start();
//            }
//            float gl_x1, gl_x2, gl_y1, gl_y2;
//
//
//            gl_x1 = ((2 * (x1 / cvWidth)) - 1) * (cvWidth / width);
//            gl_x2 = ((2 * (x2 / cvWidth)) - 1) * (cvWidth / width);
//            gl_y1 = ((2 * (y1 / cvHeight)) - 1) * (cvHeight / height);
//            gl_y2 = ((2 * (y2 / cvHeight)) - 1) * (cvHeight / height);
//
//
//            faceFrames.add(new float[]{gl_x1, -gl_y1, gl_x2, -gl_y2, 0.01f});
//            break;
//        }
//
//        mErRender.getFaceFrames().clear();
//        mErRender.getFaceFrames().addAll(faceFrames);
//        mGLSurfaceView.requestRender();
//        frame.getData().release();
//    }
//
//    class RunnableM implements Runnable {
//        public Mat fr;
//        public int x1;
//        public int y1;
//        public int x2;
//        public int y2;
//        public int x;
//        public int y;
//
//        @Override
//        public void run() {
//            try {
//                t = true;
//                Rect roi = new Rect(x1, y1, x2 - x1, y2 - y1);
//                Mat crop = new Mat(fr, roi);
//                Size sz = new Size(64, 64);
//                Mat resize = new Mat();
//                Imgproc.resize(crop, resize, sz);
//
//                byte[] arr = new byte[64 * 64];
//                resize.get(0, 0, arr);
//
//                List<EmotionClassifier.Recognition> c = classifier.recognizeImage(arr);
//                String s = c.get(0).getTitle();
//
//                String finalS = s;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast toast = Toast.makeText(MainActivity.this, finalS, Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.BOTTOM, x1, y2);
//                        toast.show();
//                    }
//                });
//                //runOnUiThread(() -> Toast.makeText(MainActivity.this, finalS, Toast.LENGTH_SHORT).show(););
////            synchronized (this) {
////                try {
////                    this.wait(500);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
//                t = false;
//            }catch (Exception ignore){
//
//            }
//        }
//    }
}