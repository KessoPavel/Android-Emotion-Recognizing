package com.kesso.er.detector.output

import com.kesso.er.detector.input.IDetectorInput.IFace

interface IDetectorOutput {
    fun receive(faceList: List<IFace>)
}