package com.kesso.er.search.input.CameraInput

import com.kesso.er.search.input.BaseInput.IBaseInput
import com.kesso.er.search.input.CameraInput.ErCamera.ErCamera

interface ICameraBaseInput: IBaseInput {
    fun open(visible: Boolean): Boolean
    var availableCameras: Array<ErCamera>
    var currentCamera: ErCamera
}