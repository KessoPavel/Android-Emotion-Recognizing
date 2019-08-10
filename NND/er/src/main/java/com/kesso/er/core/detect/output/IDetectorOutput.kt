package com.kesso.er.core.detect.output

import com.kesso.er.core.detect.input.IDetectorInput.IFace

interface IDetectorOutput {
    fun receive(faceList: List<IFace>)
}