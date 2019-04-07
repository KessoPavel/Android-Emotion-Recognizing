package com.example.er.Search.Input.CameraInput.ErCamera

import com.example.er.Search.Input.BaseInput.IDataInputListener
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat

class CameraViewListener(var mFrameListenerData: IDataInputListener?): CameraBridgeViewBase.CvCameraViewListener2{
    private var mGray: Mat? = null

    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
        mGray?.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        if (inputFrame != null){
            mGray = inputFrame.gray()
            if (mGray != null) {
                mFrameListenerData?.receiveFrame(CameraFrame(mGray!!, mGray!!.rows(), mGray!!.cols()))
            }
            return inputFrame.rgba()
        }
        return Mat()
    }
}