package com.kesso.er.detector.input.IDetectorInput

import com.kesso.er.detector.input.QueueBehavior.IQueueBehavior
import com.kesso.er.search.output.baseOutput.IBaseSearcherOutput

interface IDetectorInputSearcher: IBaseSearcherOutput {
    var listener: IFaceListener?
    var queueBehavior: IQueueBehavior
    fun requestData()
}