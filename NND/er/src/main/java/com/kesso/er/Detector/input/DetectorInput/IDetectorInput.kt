package com.kesso.er.Detector.input.DetectorInput

import com.kesso.er.Search.Ouptut.BaseOutput.IBaseOutput

interface IDetectorInput: IBaseOutput {
    fun open()
    fun close()
    fun setListener(listener: IFaceListener)
    fun requestData()
    fun setQueueBehavior()
}