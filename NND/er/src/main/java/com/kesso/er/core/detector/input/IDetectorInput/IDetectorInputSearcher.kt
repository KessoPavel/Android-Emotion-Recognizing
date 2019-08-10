package com.kesso.er.core.detector.input.IDetectorInput

import com.kesso.er.core.detector.input.QueueBehavior.IQueueBehavior
import com.kesso.er.core.search.output.baseOutput.IBaseSearcherOutput

interface IDetectorInputSearcher: IBaseSearcherOutput {
    var listener: IFaceListener?
    var queueBehavior: IQueueBehavior
    fun requestData()
}