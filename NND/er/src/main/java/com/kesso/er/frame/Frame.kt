package com.kesso.er.frame

import com.kesso.er.face.IFace
import org.opencv.core.Mat

class Frame(override val data: Mat, override val offset: Int) : IBaseFrame{
    override var faces: List<IFace> = emptyList()
}