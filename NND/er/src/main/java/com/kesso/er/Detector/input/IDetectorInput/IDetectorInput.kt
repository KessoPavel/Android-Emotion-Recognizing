package com.kesso.er.Detector.input.IDetectorInput

import com.kesso.er.Detector.input.QueueBehavior.IQueueBehavior
import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput

interface IDetectorInput: IBaseOutput {
    var listener: IFaceListener?
    var queueBehavior: IQueueBehavior
    fun requestData()
}