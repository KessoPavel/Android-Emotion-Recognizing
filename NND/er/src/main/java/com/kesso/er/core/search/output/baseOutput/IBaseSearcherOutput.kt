package com.kesso.er.core.search.output.baseOutput

import com.kesso.er.core.frame.IBaseFrame

interface IBaseSearcherOutput {
    fun receive(frame: IBaseFrame)
}