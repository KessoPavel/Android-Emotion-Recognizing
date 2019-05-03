package com.kesso.er.search.input.BaseInput

import org.opencv.core.Mat

interface IFrame {
    val data: Mat
    val offset: Int
    val size:   Int
}