package com.kesso.er.core.detect.input.detectorInput

import com.kesso.er.core.frame.IBaseFrame

interface IFaceListener {
    fun receive(frame: IBaseFrame)
}