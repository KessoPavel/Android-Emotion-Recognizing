package com.kesso.er.core.search.input.baseInput

import com.kesso.er.core.frame.IBaseFrame

interface IDataInputListener {
    fun receiveFrame(frame: IBaseFrame)
}