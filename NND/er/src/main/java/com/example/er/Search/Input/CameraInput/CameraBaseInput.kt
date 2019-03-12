package com.example.er.Search.Input.CameraInput

import android.hardware.Camera
import com.example.er.Search.Input.BaseInput.IFrame
import com.example.er.Search.Input.BaseInput.IInputListener
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat

class CameraBaseInput  constructor(private var mCameraBridgeViewBase: CameraBridgeViewBase) : ICameraBaseInput {
    private var mframeListener: IInputListener? = null
    private val mCameraViewListener: CameraViewListener = CameraViewListener(mframeListener)


    override fun getAvailableCameras(): Array<ErCamera> {
        val count = Camera.getNumberOfCameras();

        return when (count){
            0 -> arrayOf()
            1 -> arrayOf(ErCamera.BASE)
            else -> arrayOf(ErCamera.BASE, ErCamera.FRONT)
        }
    }

    override fun switchCamera(camera: ErCamera) {
        mCameraBridgeViewBase.setCameraIndex(camera.getCameraId())
    }

    override fun open(): Boolean {
        mCameraBridgeViewBase.visibility = CameraBridgeViewBase.VISIBLE;
        mCameraBridgeViewBase.setCvCameraViewListener(mCameraViewListener)
        mCameraBridgeViewBase.enableView()
        return true
    }

    override fun close() {
        mframeListener = null
        mCameraBridgeViewBase.disableView()
    }

    override fun setInputListener(listener: IInputListener) {
        mframeListener = listener
    }

    private class CameraViewListener(var mFrameListener: IInputListener?): CameraBridgeViewBase.CvCameraViewListener2{
        private var mGray: Mat? = null

        override fun onCameraViewStarted(width: Int, height: Int) {
            mGray = Mat()
        }

        override fun onCameraViewStopped() {
            mGray?.release()
        }

        override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
            mGray = inputFrame?.gray()
            if (mGray != null) {
                mFrameListener?.receiveFrame(CameraFrame(mGray!!, mGray!!.rows(), mGray!!.cols()))
            }

            return inputFrame?.rgba()!!
        }
    }

    class CameraFrame(
            override val data: Mat,
            override val offset: Int,
            override val size: Int) : IFrame {}
}