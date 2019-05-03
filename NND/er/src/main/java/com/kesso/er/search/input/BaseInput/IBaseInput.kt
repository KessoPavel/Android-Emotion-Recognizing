package com.kesso.er.search.input.BaseInput

interface IBaseInput {
    fun open():             Boolean
    fun close()
    fun setDataInputListener(listenerData: IDataInputListener)
}