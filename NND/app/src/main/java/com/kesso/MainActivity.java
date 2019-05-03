package com.kesso;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.kesso.er.detector.input.IDetectorInput.IFace;
import com.kesso.er.openGLWrapper.render.ErRender;
import com.kesso.er.openGLWrapper.vIew.ErGLSurfaceView;
import com.kesso.er.search.input.BaseInput.IFrame;
import com.kesso.er.search.input.CameraInput.ErCamera.ErCamera;
import com.kesso.er.search.input.CameraInput.ICameraBaseInput;
import com.kesso.er.search.output.BaseOutput.IBaseOutput;
import com.kesso.er.search.searcher.Searcher;
import com.kesso.er.search.SearcherModule;
import com.kesso.mylibrary.Classifier;

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

    Classifier classifier;

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
             classifier = Classifier.create(this, Classifier.Device.CPU, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean t = false;
    @Override
    public void receive(IFrame frame, final List<? extends IFace> searchFaces) {
        List<float[]> faceFrames = new ArrayList<>();
        List<Mat> faces = new ArrayList<>();


        for (IFace face : searchFaces){
            int x1 = face.getX1();
            int x2 = face.getX2();
            int y1 = face.getY1();
            int y2 = face.getY2();

            RunnableM runnableM = new RunnableM();
            runnableM.fr = frame.getData().clone();
            runnableM.x1 = x1;
            runnableM.x2 = x2;
            runnableM.y1 = y1;
            runnableM.y2 = y2;

            if (!t) {
                Thread thread = new Thread(runnableM);
                thread.start();
            }
            float gl_x1, gl_x2, gl_y1, gl_y2;
            float cvHeight = frame.getData().height();
            float cvWidth = frame.getData().width();

            gl_x1 = ((2 * (x1 / cvWidth)) - 1) * (cvWidth / width);
            gl_x2 = ((2 * (x2 / cvWidth)) - 1) * (cvWidth / width);
            gl_y1 = ((2 * (y1 / cvHeight)) - 1) * (cvHeight / height);
            gl_y2 = ((2 * (y2 / cvHeight)) - 1) * (cvHeight / height);

            faceFrames.add(new float[]{gl_x1, -gl_y1, gl_x2, -gl_y2, 0.01f});
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

        @Override
        public void run() {
            t = true;
            Rect roi = new Rect(x1,y1, x2-x1, y2-y1);
            Mat crop = new Mat(fr, roi);
            Size sz = new Size(48,48);
            Mat resize = new Mat();
            Imgproc.resize(crop, resize, sz);

            byte[] arr = new byte[48*48];
            resize.get(0,0,arr);

            List<Classifier.Recognition> c =  classifier.recognizeImage(arr);
            String s = "";
            for (Classifier.Recognition r : c){
                s += r.toString();
            }

            String finalS = s;
            runOnUiThread(() -> Toast.makeText(MainActivity.this, finalS, Toast.LENGTH_SHORT).show());
            t = false;
        }
    }

    private static Bitmap doGreyscale(byte[] array) {
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = 48;
        int height = 48;

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = array[x*width + y];
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    private void save(Bitmap bitmap){
        FileOutputStream out = null;

        String filename = new Date().getTime() + ".png";


        File sd = new File(Environment.getExternalStorageDirectory() + "/frames");
        boolean success = true;
        if (!sd.exists()) {
            success = sd.mkdir();
        }
        if (success) {
            File dest = new File(sd, filename);

            try {
                out = new FileOutputStream(dest);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}