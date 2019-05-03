package com.kesso.er.search.input.CameraInput.ErCamera

import com.kesso.er.search.input.BaseInput.IFrame
import org.opencv.core.Mat

class CameraFrame(
        override val data: Mat,
        override val offset: Int,
        override val size: Int) : IFrame {}