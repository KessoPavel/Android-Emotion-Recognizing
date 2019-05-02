package com.kesso.er.Search.Input.CameraInput.ErCamera

import com.kesso.er.Search.Input.BaseInput.IDataInputListener
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc


class CameraViewListener(var mFrameListenerData: IDataInputListener?) : CameraBridgeViewBase.CvCameraViewListener2 {
    private var mGray: Mat? = null
    private var mGrayT: Mat? = null
    private var mRgba: Mat? = null
    private var mRgbaT: Mat? = null


    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
        mGray?.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        mRgbaT?.release()
        mGrayT?.release()

        mRgba = inputFrame!!.rgba()
        mRgbaT = mRgba?.t()
        val tempRgba = mRgba?.t()
        Core.flip(tempRgba, mRgbaT, -1)
        Imgproc.resize(mRgbaT, mRgbaT, mRgba?.size())

        mGray = inputFrame!!.gray()
        mGrayT = mGray?.t()
        val tempGray = mGray?.t()
        Core.flip(tempGray, mGrayT, -1)
        Imgproc.resize(mGrayT, mGrayT, mGray?.size())

        if (mGrayT != null) {
            mFrameListenerData?.receiveFrame(CameraFrame(mGrayT!!, mGrayT!!.rows(), mGrayT!!.cols()))
        }

        mGray?.release()
        mRgba?.release()
        tempRgba?.release()
        tempGray?.release()

        return mRgbaT!!
    }
}