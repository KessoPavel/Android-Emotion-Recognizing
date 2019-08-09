package com.kesso.er.search.input.cameraInput

import android.hardware.Camera
import com.kesso.er.search.input.baseInput.IDataInputListener
import com.kesso.er.search.input.cameraInput.cameraWrapper.CameraViewListener
import com.kesso.er.search.input.cameraInput.cameraWrapper.ErCamera
import org.opencv.android.CameraBridgeViewBase

class CameraBaseInput constructor(private var mCameraBridgeViewBase: CameraBridgeViewBase) : ICameraBaseInput {
    private var mFrameListener: IDataInputListener? = null
    private var mCameraViewListener: CameraViewListener = CameraViewListener(mFrameListener)

    override var availableCameras: Array<ErCamera> = arrayOf()
        get() {
            val count = Camera.getNumberOfCameras();

            return when (count){
                0 -> arrayOf()
                1 -> arrayOf(ErCamera.BASE)
                2 -> arrayOf(ErCamera.BASE, ErCamera.FRONT)
                else -> field
            }
        }

    override var currentCamera: ErCamera = ErCamera.BASE
        set(value) = mCameraBridgeViewBase.setCameraIndex(value.getCameraId())

    override fun open(visible: Boolean): Boolean {
        mCameraBridgeViewBase.visibility =
                if (visible) CameraBridgeViewBase.VISIBLE else CameraBridgeViewBase.INVISIBLE;
        mCameraBridgeViewBase.setCvCameraViewListener(mCameraViewListener)
        mCameraBridgeViewBase.enableView()
        return true
    }

    override fun open(): Boolean {
        return open(true)
    }

    override fun close() {
        mFrameListener = null
        mCameraBridgeViewBase.disableView()
    }

    override fun setDataInputListener(listenerData: IDataInputListener) {
        mFrameListener = listenerData
        mCameraViewListener = CameraViewListener(mFrameListener)
    }
}