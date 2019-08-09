package com.kesso.er.search.output.baseOutput

import com.kesso.er.face.IFace
import com.kesso.er.frame.IBaseFrame

interface IBaseSearcherOutput {
    fun receive(frame: IBaseFrame)
}