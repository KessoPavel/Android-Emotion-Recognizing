package com.kesso.er.FaceFrameDrawer.Render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.kesso.er.FaceFrameDrawer.Frame.FaceFrame
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import java.util.Collections.synchronizedCollection
import kotlin.collections.ArrayList


class ErRender : GLSurfaceView.Renderer {
    private val modelViewMatrix = floatArrayOf(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f)

    var faceFrames: Collection<FloatArray> = Collections.synchronizedCollection(ArrayList<FloatArray>())

    init {
        //faceFrames += FaceFrame(FaceFrame.Dot(-0.636824f, -0.5868056f), FaceFrame.Dot(-0.2804054f, 0.14583337f), 0.02f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        for (array: FloatArray in faceFrames) {
            val frame = FaceFrame(FaceFrame.Dot(array[0], array[1]), FaceFrame.Dot(array[2], array[3]), array[4])
            frame.draw(modelViewMatrix)
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    companion object Loader {
        fun loadShader(type: Int, shaderCode: String): Int {
            val shader = GLES20.glCreateShader(type)
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
            return shader
        }
    }
}