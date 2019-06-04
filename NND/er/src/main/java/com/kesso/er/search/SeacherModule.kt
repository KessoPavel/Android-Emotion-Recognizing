package com.kesso.er.search

import android.content.Context
import com.kesso.er.search.input.BaseInput.IBaseInput
import com.kesso.er.search.input.CameraInput.CameraBaseInput
import com.kesso.er.search.output.BaseOutput.IBaseOutput
import com.kesso.er.search.searcher.ISearcher
import com.kesso.er.search.searcher.Searcher
import org.opencv.android.CameraBridgeViewBase

class SearcherModule(val context: Context, val cameraView: CameraBridgeViewBase, val output: IBaseOutput) {
    var input: IBaseInput? = null
    var searcher: ISearcher? = null

    fun init(){
        input = CameraBaseInput(cameraView)
        searcher = Searcher.Builder(
                context = context,
                output = output
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