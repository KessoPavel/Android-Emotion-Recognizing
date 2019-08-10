package com.kesso.er.core.search.searcher

import com.kesso.er.core.search.input.baseInput.IDataInputListener
import com.kesso.er.core.search.output.baseOutput.IBaseSearcherOutput

interface ISearcher: IDataInputListener {
    val searcherOutput: IBaseSearcherOutput?
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
