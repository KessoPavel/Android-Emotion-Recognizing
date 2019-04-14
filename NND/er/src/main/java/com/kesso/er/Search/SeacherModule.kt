package com.kesso.er.Search

import android.content.Context
import com.kesso.er.Search.Input.BaseInput.IBaseInput
import com.kesso.er.Search.Input.CameraInput.CameraBaseInput
import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput
import com.kesso.er.Search.Searcher.ISearcher
import com.kesso.er.Search.Searcher.Searcher
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