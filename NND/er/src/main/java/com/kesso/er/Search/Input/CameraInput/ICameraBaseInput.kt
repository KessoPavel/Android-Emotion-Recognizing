package com.kesso.er.Search.Input.CameraInput

import com.kesso.er.Search.Input.BaseInput.IBaseInput
import com.kesso.er.Search.Input.CameraInput.ErCamera.ErCamera

interface ICameraBaseInput: IBaseInput {
    fun open(visible: Boolean): Boolean
    var availableCameras: Array<ErCamera>
    var currentCamera: ErCamera
}