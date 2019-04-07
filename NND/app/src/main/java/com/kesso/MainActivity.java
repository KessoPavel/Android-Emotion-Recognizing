package com.kesso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.er.Search.Input.CameraInput.ErCamera.ErCamera;
import com.example.er.Search.Input.CameraInput.ICameraBaseInput;
import com.example.er.Search.SearcherModule;

import org.opencv.android.CameraBridgeViewBase;

public class MainActivity extends AppCompatActivity {
    private CameraBridgeViewBase cameraBridgeViewBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraBridgeViewBase = findViewById(R.id.fd_activity_surface_view);
        SearcherModule searcherModule = new SearcherModule(this, cameraBridgeViewBase);
        searcherModule.init();
        searcherModule.open();

        ICameraBaseInput iCameraBaseInput = (ICameraBaseInput) searcherModule.getInput();
        iCameraBaseInput.setCurrentCamera(ErCamera.FRONT);
    }
}
