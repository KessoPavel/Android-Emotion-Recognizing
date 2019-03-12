package com.example.er.Search.Searcher

import android.content.Context
import com.example.er.Search.Input.BaseInput.IBaseInput
import com.example.er.Search.Input.BaseInput.IFrame
import com.example.er.Search.Input.BaseInput.IInputListener
import com.example.er.Search.Ouptut.BaseOutput.IBaseOutput
import com.kesso.facesearchenative.NativeSearcher
import org.opencv.core.Mat
import org.opencv.core.MatOfRect

class Searcher(
        private val nativeSearcher: NativeSearcher
): ISearcher, IInputListener {
    private var mInput: IBaseInput? = null
    private var mOutput: IBaseOutput? = null
    private var mGray: Mat? = null

    var minFaceSize: Float = 0.0f
        set(value) {
            field = value

            val height = mGray?.rows()
            if (Math.round(height!! * value) > 0) {
                val mAbsoluteFaceSize = Math.round(height * value)
                nativeSearcher.setMinFaceSize(mAbsoluteFaceSize)
            }
        }

    override fun setInput(input: IBaseInput) {
        mInput = input
        input.setInputListener(this)
    }

    override fun setOutput(output: IBaseOutput) {
        mOutput = output
    }

    override fun start() {
        mInput?.open()
        nativeSearcher.start()
    }

    override fun stop() {
        mInput?.close()
        nativeSearcher.stop()
    }

    override fun pause() {
        mInput?.close()
        nativeSearcher.pause()
    }

    override fun resume() {
        mInput?.open()
        nativeSearcher.resume()
    }

    override fun receiveFrame(frame: IFrame) {
        val faces = MatOfRect()
        nativeSearcher.detect(frame.data, faces)

        for (face in faces.toArray()){

        }
    }

    data class Builder(val context: Context,
                       var minFaceSize: Float = 0.2f,
                       var input: IBaseInput,
                       var output: IBaseOutput){
        fun minFaceSize(minFaceSize: Float) = apply { this.minFaceSize = minFaceSize }
        fun input(input: IBaseInput) = apply { this.input = input }
        fun output(output: IBaseOutput) = apply { this.output = output}


        fun build(): Searcher {
            val searcher = Searcher(NativeSearcher(context, 0))
            searcher.minFaceSize = minFaceSize
            searcher.setInput(input)
            searcher.setOutput(output)

            return searcher
        }
    }
}