package com.example.er.Search.Ouptut.BaseOutput

import com.example.er.Search.Input.BaseInput.IFrame

interface IBaseOutput {
    fun receive(frame: IFrame, searchFaces: Array<Face>): Void
}