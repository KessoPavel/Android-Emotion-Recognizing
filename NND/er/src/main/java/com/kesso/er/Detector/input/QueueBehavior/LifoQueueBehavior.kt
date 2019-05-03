package com.kesso.er.Detector.input.QueueBehavior

import com.kesso.er.Detector.input.DetectorInput.IFace

class LifoQueueBehavior: IQueueBehavior {
    var currentFace: IFace? = null

    override fun addFace(face: IFace) {
        currentFace = face
    }

    override fun getNext(): IFace? {
        val temp = currentFace
        currentFace = null
        return temp
    }
}