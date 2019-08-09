package com.kesso.er.search.output.baseOutput

import com.kesso.er.face.IFace
import com.kesso.er.frame.IFrameWithFace

interface IBaseSearcherOutput {
    fun receive(frame: IFrameWithFace)
}