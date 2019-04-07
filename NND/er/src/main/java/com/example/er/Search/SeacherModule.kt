package com.example.er.Search

import android.content.Context
import com.example.er.Search.Input.BaseInput.IBaseInput
import com.example.er.Search.Input.BaseInput.IFrame
import com.example.er.Search.Input.CameraInput.CameraBaseInput
import com.example.er.Search.Ouptut.BaseOutput.Face
import com.example.er.Search.Ouptut.BaseOutput.IBaseOutput
import com.example.er.Search.Searcher.ISearcher
import com.example.er.Search.Searcher.Searcher
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