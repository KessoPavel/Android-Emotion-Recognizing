package com.example.er.Search.Input.BaseInput

interface IBaseInput {
    fun open():             Boolean
    fun close():            Void
    fun setInputListener(listener: IInputListener): Void
}