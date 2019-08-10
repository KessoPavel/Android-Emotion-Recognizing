package com.kesso.er.core.detect.input.queueBehavior

import com.kesso.er.core.frame.IBaseFrame
import java.util.*

class LiloQueueBehavior: IQueueBehavior {
    var queue: MutableList<IBaseFrame> = LinkedList()

    override fun getNext(): IBaseFrame? {
        if (queue.size > 0)
            return queue.removeAt(0)
        return null
    }

    override fun addFrame(frame: IBaseFrame) {
        queue.add(frame)
    }
}