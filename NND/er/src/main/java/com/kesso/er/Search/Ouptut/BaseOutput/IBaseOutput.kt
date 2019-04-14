package com.kesso.er.Search.Ouptut.BaseOutput

import com.kesso.er.Search.Input.BaseInput.IFrame

interface IBaseOutput {
    fun receive(frame: IFrame, searchFaces: List<Face>)
}