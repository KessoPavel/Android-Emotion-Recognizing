package com.kesso.er.Detector.input.QueueBehavior

import com.kesso.er.Detector.input.DetectorInput.IFace
import java.util.*

class LiloQueueBehavior: IQueueBehavior {
    var queue: MutableList<IFace> = LinkedList()

    override fun getNext(): IFace? {
        if (queue.size > 0)
            return queue.removeAt(0)
        return null
    }

    override fun addFace(face: IFace) {
        queue.add(face)
    }
}