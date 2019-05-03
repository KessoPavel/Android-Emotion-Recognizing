package com.kesso.er.search.output.BaseOutput

import com.kesso.er.detector.input.IDetectorInput.IFace

interface IBaseOutput {
    fun receive(searchFaces: List<IFace>)
}