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

class SearcherModule(val context: Context, val cameraView: CameraBridgeViewBase) {
    var input: IBaseInput? = null
    var output: IBaseOutput? = null
    var searcher: ISearcher? = null

    fun init(){
        input = CameraBaseInput(cameraView)
        searcher = Searcher.Builder(
                context = context,
                output = object : IBaseOutput {
                    override fun receive(frame: IFrame, searchFaces: List<Face>) {
                    }
                }
        ).build()
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