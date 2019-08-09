package com.kesso.er.search.searcher

import android.content.Context
import com.kesso.er.face.Face
import com.kesso.er.frame.IBaseFrame
import com.kesso.er.search.output.baseOutput.IBaseSearcherOutput
import com.kesso.facesearchenative.NativeSearcher
import org.opencv.core.Mat
import org.opencv.core.MatOfRect

class Searcher(
        private val nativeSearcher: NativeSearcher):
        ISearcher {
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

    override var searcherOutput: IBaseSearcherOutput? = null

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

    override fun receiveFrame(frame: IBaseFrame) {
        val faces = MatOfRect()
        val temp = frame.data

        nativeSearcher.detect(temp, faces)
        frame.faces = Face.getFaces(faces)
        searcherOutput?.receive(frame)

        temp.release()
        frame.data.release()
    }

    data class Builder(val context: Context,
                       var minFaceSize: Float = 0.2f,
                       var searcherOutput: IBaseSearcherOutput){
        fun minFaceSize(minFaceSize: Float) = apply { this.minFaceSize = minFaceSize }
        fun output(searcherOutput: IBaseSearcherOutput) = apply { this.searcherOutput = searcherOutput}


        fun build(): Searcher {
            val searcher = Searcher(NativeSearcher(context, 0))
            searcher.minFaceSize = minFaceSize
            searcher.searcherOutput = searcherOutput
            return searcher
        }
    }
}