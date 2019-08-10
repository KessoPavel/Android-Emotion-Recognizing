package com.kesso.er.core.drawer

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.view.View
import com.kesso.er.core.detect.input.detectorInput.IFace
import com.kesso.er.core.detect.output.IDetectorOutput
import com.kesso.er.openGLWrapper.render.ErRender
import com.kesso.er.openGLWrapper.vIew.ErGLSurfaceView
import java.util.*

class EmotionDrawer(val context: Context, val view: ConstraintLayout, val width: Int, val height: Int) : IDetectorOutput {
    private var mGLSurfaceView: ErGLSurfaceView? = null
    private var mErRender: ErRender? = null


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun init(){
        mGLSurfaceView = ErGLSurfaceView(context)
        mGLSurfaceView?.setId(View.generateViewId())
        mGLSurfaceView?.setZOrderOnTop(true)

        val constraintSet = ConstraintSet()
        view.addView(mGLSurfaceView)
        constraintSet.clone(view)
        constraintSet.connect(mGLSurfaceView?.id!!, ConstraintSet.TOP, view.getId(), ConstraintSet.TOP, 0)
        constraintSet.applyTo(view)

        mErRender = mGLSurfaceView?.erRender
    }

    override fun receive(faceList: List<IFace>) {
        var faceFrames: Collection<FloatArray> = Collections.synchronizedCollection(ArrayList<FloatArray>())

        for (face in faceList) {
            val x1 = face.x1
            val x2 = face.x2
            val y1 = face.y1
            val y2 = face.y2

            val cvHeight = face.frame.data.height().toFloat()
            val cvWidth = face.frame.data.width().toFloat()

            val gl_x1: Float
            val gl_x2: Float
            val gl_y1: Float
            val gl_y2: Float

            gl_x1 = (2 * (x1 / cvWidth) - 1) * (cvWidth / width)
            gl_x2 = (2 * (x2 / cvWidth) - 1) * (cvWidth / width)
            gl_y1 = (2 * (y1 / cvHeight) - 1) * (cvHeight / height)
            gl_y2 = (2 * (y2 / cvHeight) - 1) * (cvHeight / height)


            faceFrames += floatArrayOf(gl_x1, -gl_y1, gl_x2, -gl_y2, 0.01f)
            break
        }

        mErRender?.clear()
        mErRender?.faceFrames = faceFrames
        mGLSurfaceView?.requestRender()
    }
}