package com.kesso.er.core.detect.input.QueueBehavior

import com.kesso.er.core.frame.Frame
import com.kesso.er.core.frame.IBaseFrame

class LifoQueueBehavior: IQueueBehavior {
    var currentFace: IBaseFrame? = null

    override fun addFrame(frame: IBaseFrame) {
        currentFace = frame
    }

    override fun getNext(): IBaseFrame? {
        val temp = currentFace
        currentFace = null
        return temp
    }
}