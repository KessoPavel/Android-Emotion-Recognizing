package com.kesso.er.core.detector.input

import com.kesso.er.core.detector.input.IDetectorInput.IFace
import com.kesso.er.core.detector.input.IDetectorInput.IFaceListener
import com.kesso.er.core.detector.input.QueueBehavior.IQueueBehavior
import com.kesso.er.core.search.input.BaseInput.IFrame

class DetectorInput(
<<<<<<< HEAD
        override var listener: IFaceListener?,
        override var queueBehavior: IQueueBehavior) : IDetectorInputSearcher {
=======
        override var queueBehavior: IQueueBehavior) : IDetectorInput {
>>>>>>> e4830639ea0791aa3fc426aeb4955b4c8f703e6c

    override var listener: IFaceListener? = null

    private var request: Boolean = false

    override fun requestData() {
        val faces = queueBehavior.getNext()
        if (faces == null){
            request = true
        } else {
            listener?.receive(preprocessFaces(faces))
        }
    }

    private fun preprocessFaces(faces: List<IFace>): List<IFace> {
        return faces
    }

    override fun receive(frame: IFrame, searchFaces: List<IFace>) {
        if (request){
            request = false
            listener?.receive(preprocessFaces(searchFaces))
        } else {
            queueBehavior.addFace(searchFaces)
        }
    }
}