package com.kesso.er.search

import android.content.Context
import com.kesso.er.search.input.baseInput.IBaseInput
import com.kesso.er.search.input.cameraInput.CameraBaseInput
import com.kesso.er.search.output.baseOutput.IBaseSearcherOutput
import com.kesso.er.search.searcher.ISearcher
import com.kesso.er.search.searcher.Searcher
import org.opencv.android.CameraBridgeViewBase

class SearcherModule(
        val context: Context,
        val cameraView: CameraBridgeViewBase,
        val searcherOutput: IBaseSearcherOutput) {

    var input: IBaseInput? = null
    var searcher: ISearcher? = null

    fun init(){
        input = CameraBaseInput(cameraView)
        searcher = Searcher.Builder(
                context = context,
                searcherOutput = searcherOutput
        ).build()

        input?.setDataInputListener(searcher as Searcher)
    }

    fun open(){
        input?.open()
        searcher?.start()
    }

    fun close(){
        input?.close()
        searcher?.stop()
    }
}