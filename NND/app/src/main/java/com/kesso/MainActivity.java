package com.kesso;

import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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

import org.opencv.android.CameraBridgeViewBase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IBaseOutput {
    private CameraBridgeViewBase cameraBridgeViewBase;
    private ErGLSurfaceView mGLSurfaceView;
    private ErRender mErRender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
    }

    boolean t = true;
    @Override
    public void receive(IFrame frame, final List<Face> searchFaces) {
        List<float[]> faceFrames = new ArrayList<>();
        for (Face face : searchFaces){
            int x1 = face.getX1();
            int x2 = face.getX2();
            int y1 = face.getY1();
            int y2 = face.getY2();

            float gl_x1, gl_x2, gl_y1, gl_y2;
            float screenHeight = cameraBridgeViewBase.getHeight();
            float screenWidth = cameraBridgeViewBase.getWidth();

            gl_x1 = (2 * (x1 / screenWidth)) - 1;
            gl_x2 = (2 * (x2 / screenWidth)) - 1;
            gl_y1 = (2 * (y1 / screenHeight)) - 1;
            gl_y2 = (2 * (y2 / screenHeight)) - 1;

            faceFrames.add(new float[]{gl_x1, -gl_y1, gl_x2, -gl_y2, 0.01f});
        }

        mErRender.getFaceFrames().clear();
        mErRender.getFaceFrames().addAll(faceFrames);
        mGLSurfaceView.requestRender();

    }
}