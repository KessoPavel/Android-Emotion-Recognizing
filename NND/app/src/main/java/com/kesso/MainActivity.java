package com.kesso;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kesso.er.Search.Input.BaseInput.IFrame;
import com.kesso.er.Search.Input.CameraInput.ErCamera.ErCamera;
import com.kesso.er.Search.Input.CameraInput.ICameraBaseInput;
import com.kesso.er.Search.Ouptut.BaseOutput.Face;
import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput;
import com.kesso.er.Search.Searcher.Searcher;
import com.kesso.er.Search.SearcherModule;

import org.opencv.android.CameraBridgeViewBase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IBaseOutput {
    private CameraBridgeViewBase cameraBridgeViewBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraBridgeViewBase = findViewById(R.id.fd_activity_surface_view);
        SearcherModule searcherModule = new SearcherModule(this, cameraBridgeViewBase, this);
        searcherModule.init();
        searcherModule.open();

        ICameraBaseInput iCameraBaseInput = (ICameraBaseInput) searcherModule.getInput();
        iCameraBaseInput.setCurrentCamera(ErCamera.FRONT);
        ((Searcher)searcherModule.getSearcher()).setMinFaceSize((float) 0.2);
    }

    @Override
    public void receive(IFrame frame, final List<Face> searchFaces) {
        runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {

            }
        });
    }
}