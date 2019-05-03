package com.kesso.er.Detector.input.QueueBehavior

import com.kesso.er.Detector.input.IDetectorInput.IFace

class LifoQueueBehavior: IQueueBehavior {
    var currentFace: List<IFace>? = null

    override fun addFace(face: List<IFace>) {
        currentFace = face
    }

    override fun getNext(): List<IFace>? {
        val temp = currentFace
        currentFace = null
        return temp
    }
}