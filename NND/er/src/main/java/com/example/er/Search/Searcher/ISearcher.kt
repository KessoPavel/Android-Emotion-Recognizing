package com.example.er.Search.Searcher

import com.example.er.Search.Input.BaseInput.IBaseInput
import com.example.er.Search.Ouptut.BaseOutput.IBaseOutput
import java.io.IOError

interface ISearcher {
    fun setOutput(output: IBaseOutput)
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
