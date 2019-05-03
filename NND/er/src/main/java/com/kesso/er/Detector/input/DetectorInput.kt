package com.kesso.er.Detector.input

import com.kesso.er.Detector.input.IDetectorInput.IDetectorInput
import com.kesso.er.Detector.input.IDetectorInput.IFace
import com.kesso.er.Detector.input.IDetectorInput.IFaceListener
import com.kesso.er.Detector.input.QueueBehavior.IQueueBehavior
class DetectorInput(
        override var listener: IFaceListener?,
        override var queueBehavior: IQueueBehavior) : IDetectorInput {

    private var request: Boolean = false


    override fun requestData() {
        val faces = queueBehavior.getNext()
        if (faces == null){
            request = true
        } else {
            listener?.receive(preworkFaces(faces))
        }
    }

    private fun preworkFaces(faces: List<IFace>): List<IFace> {
        return faces
    }

    override fun receive(searchFaces: List<IFace>) {
        if (request){
            request = false
            listener?.receive(preworkFaces(searchFaces))
        } else {
            queueBehavior.addFace(searchFaces)
        }
    }
}