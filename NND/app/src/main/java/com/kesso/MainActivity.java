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
import com.kesso.er.core.search.input.cameraInput.ICameraBaseInput;
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
import java.util.Objects;

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
}