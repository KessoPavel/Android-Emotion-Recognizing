package com.kesso.er.search.input.baseInput

interface IBaseInput {
    fun open():             Boolean
    fun close()
    fun setDataInputListener(listenerData: IDataInputListener)
}