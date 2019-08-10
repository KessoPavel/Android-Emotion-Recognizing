package com.kesso.er.core.detect.input.IDetectorInput

import com.kesso.er.core.detect.input.QueueBehavior.IQueueBehavior
import com.kesso.er.core.search.output.baseOutput.IBaseSearcherOutput

interface IDetectorInput: IBaseSearcherOutput {
    var listener: IFaceListener?
    var queueBehavior: IQueueBehavior
    fun requestData()
}