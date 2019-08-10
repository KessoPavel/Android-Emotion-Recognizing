package com.kesso.er.core.detect.input

import com.kesso.er.core.detect.input.detectorInput.IDetectorInput
import com.kesso.er.core.detect.input.detectorInput.IFaceListener
import com.kesso.er.core.detect.input.queueBehavior.IQueueBehavior
import com.kesso.er.core.frame.IBaseFrame

class DetectorInput(override var queueBehavior: IQueueBehavior) : IDetectorInput {

    override var listener: IFaceListener? = null
    private var request: Boolean = false

    override fun requestData() {
        val frame = queueBehavior.getNext()
        if (frame == null){
            request = true
        } else {
            listener?.receive(framePreProcessing(frame))
        }
    }

    private fun framePreProcessing(frame: IBaseFrame): IBaseFrame {
        return frame
    }

    override fun receive(frame: IBaseFrame) {
        if (request){
            request = false
            listener?.receive(framePreProcessing(frame))
        } else {
            queueBehavior.addFrame(frame)
        }
    }
}