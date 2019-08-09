package com.kesso.er.search.input.baseInput

import com.kesso.er.frame.IBaseFrame

interface IDataInputListener {
    fun receiveFrame(frame: IBaseFrame)
}