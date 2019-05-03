package com.kesso.er.detector.input.QueueBehavior

import com.kesso.er.detector.input.IDetectorInput.IFace

interface IQueueBehavior {
    fun addFace(faces: List<IFace>)
    fun getNext(): List<IFace>?
}