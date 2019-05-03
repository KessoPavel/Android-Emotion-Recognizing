package com.kesso.er.Search.Ouptut.BaseOutput

import com.kesso.er.Detector.input.IDetectorInput.IFace
import com.kesso.er.Search.Input.BaseInput.IFrame

interface IBaseOutput {
    fun receive(searchFaces: List<IFace>)
}