package com.kesso.er.Detector.input.QueueBehavior

import com.kesso.er.Detector.input.IDetectorInput.IFace

interface IQueueBehavior {
    fun addFace(faces: List<IFace>)
    fun getNext(): List<IFace>?
}