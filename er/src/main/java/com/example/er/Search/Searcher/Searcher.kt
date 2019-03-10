package com.example.er.Search.Searcher

import com.example.er.Search.Input.BaseInput.IBaseInput
import com.example.er.Search.Ouptut.BaseOutput.IBaseOutput

class Searcher(): ISearcher {
    private var mInput: IBaseInput? = null
    private var mOutput: IBaseOutput? = null
    var searcherType: SearcherType = SearcherType.Native
        set(value){
            field = value
            TODO() // add change detector
        }

    var minFaceSize: Float = 0.0f
        set(value) {
            field = value
            TODO()
        }

    override fun setInput(input: IBaseInput) {
        mInput = input
    }

    override fun setOutput(output: IBaseOutput) {
        mOutput = output
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun receive() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Builder(
            var searcherType: SearcherType,
            var minFaceSize: Float
    ){
        fun searcherType(searcherType: SearcherType) = apply { this.searcherType = searcherType }
        fun minFaceSize(minFaceSize: Float) = apply { this.minFaceSize = minFaceSize }

        fun build(): Searcher {
            val searcher = Searcher()
            searcher.searcherType = searcherType
            searcher.minFaceSize = minFaceSize
            return searcher
        }
    }
}