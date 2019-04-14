package com.kesso.er.FaceFrameDrawer.VIew

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import com.kesso.er.FaceFrameDrawer.Render.ErRender

class ErGLSurfaceView(context: Context) : GLSurfaceView(context) {
    private val RedSize         = 8
    private val GreenSize       = 8
    private val BlueSize        = 8
    private val AlphaSize       = 8
    private val DepthSize       = 8
    private val StencilSize     = 8
    private val ClientVersion   = 2

    var erRender: ErRender? = null
        private set

    init {
        setEGLContextClientVersion(ClientVersion)
        setEGLConfigChooser(RedSize, GreenSize, BlueSize, AlphaSize, DepthSize, StencilSize)
        erRender = ErRender()
        setRenderer(erRender)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }
}