package com.kesso.er.search.searcher

import com.kesso.er.search.input.BaseInput.IDataInputListener
import com.kesso.er.search.output.BaseOutput.IBaseOutput

interface ISearcher: IDataInputListener {
    fun setOutput(output: IBaseOutput)
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
