package com.kesso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.er.Search.Input.BaseInput.IFrame;
import com.example.er.Search.Input.CameraInput.ErCamera.ErCamera;
import com.example.er.Search.Input.CameraInput.ICameraBaseInput;
import com.example.er.Search.Ouptut.BaseOutput.Face;
import com.example.er.Search.Ouptut.BaseOutput.IBaseOutput;
import com.example.er.Search.Searcher.Searcher;
import com.example.er.Search.SearcherModule;

import org.opencv.android.CameraBridgeViewBase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IBaseOutput {
    private CameraBridgeViewBase cameraBridgeViewBase;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraBridgeViewBase = findViewById(R.id.fd_activity_surface_view);
        textView = findViewById(R.id.textView);
        SearcherModule searcherModule = new SearcherModule(this, cameraBridgeViewBase, this);
        searcherModule.init();
        searcherModule.open();

        ICameraBaseInput iCameraBaseInput = (ICameraBaseInput) searcherModule.getInput();
        iCameraBaseInput.setCurrentCamera(ErCamera.FRONT);
        ((Searcher)searcherModule.getSearcher()).setMinFaceSize((float) 0.2);
    }

    @Override
    public void receive(IFrame frame, List<Face> searchFaces) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("1");
            }
        });
    }
}