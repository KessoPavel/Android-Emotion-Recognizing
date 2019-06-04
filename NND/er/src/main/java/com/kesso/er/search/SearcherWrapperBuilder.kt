package com.kesso.er.search

import android.content.Context
import android.view.View
import com.kesso.er.search.output.BaseOutput.IBaseOutput
import org.opencv.android.CameraBridgeViewBase

class SearcherWrapperBuilder() {
    var context: Context? = null
    var view: CameraBridgeViewBase? = null
    var output: IBaseOutput? = null

    fun context(context: Context) = apply { this.context = context }
    fun view(view: CameraBridgeViewBase) = apply { this.view = view }
    fun output(output: IBaseOutput) = apply { this.output = output }

    fun build(): SearcherWrapper {
        val searcherWrapper = SearcherWrapper(context!!, view!!, output!!)
        return searcherWrapper
    }
}