package com.kesso.er.detector.input.IDetectorInput

import com.kesso.er.search.input.BaseInput.IFrame

interface IFace {
    var data: ByteArray
    val width: Int
    val height: Int
    val emotion: Int

    val frame: IFrame
    val x1: Int
    val x2: Int
    val y1: Int
    val y2: Int
}