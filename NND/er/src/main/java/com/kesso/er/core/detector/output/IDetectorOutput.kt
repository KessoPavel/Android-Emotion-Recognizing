package com.kesso.er.core.detector.output

import com.kesso.er.core.detector.input.IDetectorInput.IFace

interface IDetectorOutput {
    fun receive(faceList: List<IFace>)
}