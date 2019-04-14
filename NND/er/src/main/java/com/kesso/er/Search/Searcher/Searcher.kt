package com.kesso.er.Search.Searcher

import android.content.Context
import com.kesso.er.Search.Input.BaseInput.IFrame
import com.kesso.er.Search.Ouptut.BaseOutput.Face
import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput
import com.kesso.facesearchenative.NativeSearcher
import org.opencv.core.Mat
import org.opencv.core.MatOfRect

class Searcher(
        private val nativeSearcher: NativeSearcher):
        ISearcher {
    private var mOutput: IBaseOutput? = null
    private var mGray: Mat? = null

    var minFaceSize: Float = 0.0f
        set(value) {
            field = value

            if  (mGray != null) {
                val height = mGray?.rows()
                if (Math.round(height!! * value) > 0) {
                    val mAbsoluteFaceSize = Math.round(height * value)
                    nativeSearcher.setMinFaceSize(mAbsoluteFaceSize)
                }
            }
        }

    override fun setOutput(output: IBaseOutput) {
        mOutput = output
    }

    override fun start() {
        nativeSearcher.start()
    }

    override fun stop() {
        nativeSearcher.stop()
    }

    override fun pause() {
        nativeSearcher.pause()
    }

    override fun resume() {
        nativeSearcher.resume()
    }

    override fun receiveFrame(frame: IFrame) {
        val faces = MatOfRect()
        nativeSearcher.detect(frame.data, faces)
        mOutput?.receive(frame, Face.Converter.getFaces(faces))
    }

    data class Builder(val context: Context,
                       var minFaceSize: Float = 0.2f,
                       var output: IBaseOutput){
        fun minFaceSize(minFaceSize: Float) = apply { this.minFaceSize = minFaceSize }
        fun output(output: IBaseOutput) = apply { this.output = output}


        fun build(): Searcher {
            val searcher = Searcher(NativeSearcher(context, 0))
            searcher.minFaceSize = minFaceSize
            searcher.setOutput(output)
            return searcher
        }
    }
}