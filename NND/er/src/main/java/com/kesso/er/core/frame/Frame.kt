package com.kesso.er.core.frame

import com.kesso.er.core.face.IFace
import org.opencv.core.Mat

class Frame(override val data: Mat, override val offset: Int) : IBaseFrame{
    override var faces: List<IFace> = emptyList()
}