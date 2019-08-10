package com.kesso.er.core.detect.input.detectorInput

import com.kesso.er.core.detect.input.queueBehavior.IQueueBehavior
import com.kesso.er.core.search.output.baseOutput.IBaseSearcherOutput

interface IDetectorInput: IBaseSearcherOutput {
    var listener: IFaceListener?
    var queueBehavior: IQueueBehavior
    fun requestData()
}