package com.kesso.er.core.detect.input.QueueBehavior

import com.kesso.er.core.frame.IBaseFrame

interface IQueueBehavior {
    fun addFrame(frame: IBaseFrame)
    fun getNext(): IBaseFrame?
}