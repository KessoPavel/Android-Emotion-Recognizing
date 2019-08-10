package com.kesso.er.core.detect.input.queueBehavior

import com.kesso.er.core.frame.IBaseFrame

interface IQueueBehavior {
    fun addFrame(frame: IBaseFrame)
    fun getNext(): IBaseFrame?
}