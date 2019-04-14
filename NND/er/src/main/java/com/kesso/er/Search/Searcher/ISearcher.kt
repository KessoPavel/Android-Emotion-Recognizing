package com.kesso.er.Search.Searcher

import com.kesso.er.Search.Input.BaseInput.IDataInputListener
import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput

interface ISearcher: IDataInputListener {
    fun setOutput(output: IBaseOutput)
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
