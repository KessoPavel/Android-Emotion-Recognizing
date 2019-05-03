package com.kesso.er.detector.input.IDetectorInput

import com.kesso.er.detector.input.QueueBehavior.IQueueBehavior
import com.kesso.er.search.output.BaseOutput.IBaseOutput

interface IDetectorInput: IBaseOutput {
    var listener: IFaceListener?
    var queueBehavior: IQueueBehavior
    fun requestData()
}