package com.kesso.er.OpenCVLoader

import android.content.Context
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader

class OpenCVLoaderHelper(val mContext: Context, callback: OpenCVLoaderHelperCallback) {
    private val mOpenCVLoaderCallback: OpenCVLoaderCallback = OpenCVLoaderCallback(mContext, callback)

    fun load(){
        if (OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, mContext, mOpenCVLoaderCallback)
        } else {
            mOpenCVLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    private class OpenCVLoaderCallback(AppContext: Context?,val callback: OpenCVLoaderHelperCallback) : BaseLoaderCallback(AppContext) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> callback.success()
                else -> callback.error()
            }
        }
    }

    interface OpenCVLoaderHelperCallback {
        fun success()
        fun error()
    }
}