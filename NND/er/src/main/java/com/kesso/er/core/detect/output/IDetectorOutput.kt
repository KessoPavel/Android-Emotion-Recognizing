package com.kesso.er.core.detect.output

import com.kesso.er.core.frame.IBaseFrame

interface IDetectorOutput {
    fun receive(frame: IBaseFrame)
}