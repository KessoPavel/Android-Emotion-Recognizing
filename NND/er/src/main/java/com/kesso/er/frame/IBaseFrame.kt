package com.kesso.er.frame

import com.kesso.er.face.IFace
import org.opencv.core.Mat

interface IBaseFrame {
    val data: Mat
    val offset: Int
    var faces: List<IFace>
}