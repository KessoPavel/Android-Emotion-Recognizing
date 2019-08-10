package com.kesso.er.core.detector.input.QueueBehavior

import com.kesso.er.core.detector.input.IDetectorInput.IFace

interface IQueueBehavior {
    fun addFace(faces: List<IFace>)
    fun getNext(): List<IFace>?
}