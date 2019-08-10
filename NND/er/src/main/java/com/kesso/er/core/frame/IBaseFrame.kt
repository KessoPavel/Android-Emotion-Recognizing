package com.kesso.er.core.frame

import com.kesso.er.core.face.IFace
import org.opencv.core.Mat

interface IBaseFrame {
    val data: Mat
    val offset: Int
    var faces: List<IFace>
}