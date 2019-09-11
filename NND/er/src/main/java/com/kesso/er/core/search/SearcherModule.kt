package com.kesso.er.core.search

import android.content.Context
import com.kesso.er.core.search.input.baseInput.IBaseInput
import com.kesso.er.core.search.input.cameraInput.CameraBaseInput
import com.kesso.er.core.search.input.cameraInput.ICameraBaseInput
import com.kesso.er.core.search.input.cameraInput.cameraWrapper.ErCamera
import com.kesso.er.core.search.output.baseOutput.IBaseSearcherOutput
import com.kesso.er.core.search.searcher.ISearcher
import com.kesso.er.core.search.searcher.Searcher
import org.opencv.android.CameraBridgeViewBase

class SearcherModule(
        val context: Context,
        val cameraView: CameraBridgeViewBase) {

    var input: IBaseInput? = null
    var searcher: ISearcher? = null
    var searcherOutput: IBaseSearcherOutput? = null

    fun init(){
        input = CameraBaseInput(cameraView)
        searcher = Searcher.Builder(
                context = context,
                searcherOutput = searcherOutput!!
        ).build()

        input?.setDataInputListener(searcher as Searcher)
        (input as ICameraBaseInput).currentCamera = ErCamera.FRONT
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