package com.example.er.Search.Input.CameraInput.ErCamera

import com.example.er.Search.Input.BaseInput.IFrame
import org.opencv.core.Mat

class CameraFrame(
        override val data: Mat,
        override val offset: Int,
        override val size: Int) : IFrame {}