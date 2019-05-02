package com.kesso;

import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kesso.er.FaceFrameDrawer.Frame.FaceFrame;
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

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
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

    boolean t = true;
    @Override
    public void receive(IFrame frame, final List<Face> searchFaces) {
        List<float[]> faceFrames = new ArrayList<>();
        List<Mat> faces = new ArrayList<>();


        for (Face face : searchFaces){
            int x1 = face.getX1();
            int x2 = face.getX2();
            int y1 = face.getY1();
            int y2 = face.getY2();

            Rect roi = new Rect(x1,y1, x2-x1, y2-y1);
            Mat crop = new Mat(frame.getData(), roi);
            Size sz = new Size(48,48);
            Mat resize = new Mat();
            Imgproc.resize(crop, resize, sz);

            byte[] arr = new byte[48*48];
            resize.get(0 ,0, arr);

            List<Classifier.Recognition> c =  classifier.recognizeImage(arr);
            String s = "";
            for (Classifier.Recognition r : c){
                s += r.toString();
            }

            String finalS = s;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, finalS, Toast.LENGTH_SHORT).show();
                }
            });

            faces.add(resize);

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
}