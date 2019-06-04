package com.kesso.er.search.output.BaseOutput

import com.kesso.er.detector.input.IDetectorInput.IFace
import com.kesso.er.search.input.BaseInput.IFrame

interface IBaseOutput {
    fun receive(frame: IFrame, searchFaces: List<IFace>)
}