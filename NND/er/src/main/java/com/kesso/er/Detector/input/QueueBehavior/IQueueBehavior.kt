package com.kesso.er.Detector.input.QueueBehavior

import com.kesso.er.Detector.input.DetectorInput.IFace

interface IQueueBehavior {
    fun addFace(face: IFace)
    fun getNext(): IFace?
}