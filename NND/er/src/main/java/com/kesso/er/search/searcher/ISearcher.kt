package com.kesso.er.search.searcher

import com.kesso.er.search.input.baseInput.IDataInputListener
import com.kesso.er.search.output.baseOutput.IBaseSearcherOutput

interface ISearcher: IDataInputListener {
    val searcherOutput: IBaseSearcherOutput?
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
