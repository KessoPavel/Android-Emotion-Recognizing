package com.kesso.er.Detector.input.QueueBehavior

import com.kesso.er.Detector.input.IDetectorInput.IFace
import java.util.*

class LiloQueueBehavior: IQueueBehavior {
    var queue: MutableList<List<IFace>> = LinkedList()

    override fun getNext(): List<IFace>? {
        if (queue.size > 0)
            return queue.removeAt(0)
        return null
    }

    override fun addFace(face: List<IFace>) {
        queue.add(face)
    }
}