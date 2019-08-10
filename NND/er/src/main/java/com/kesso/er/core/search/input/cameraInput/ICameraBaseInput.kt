package com.kesso.er.core.search.input.cameraInput

import com.kesso.er.core.search.input.baseInput.IBaseInput
import com.kesso.er.core.search.input.cameraInput.cameraWrapper.ErCamera

interface ICameraBaseInput: IBaseInput {
    fun open(visible: Boolean): Boolean
    var availableCameras: Array<ErCamera>
    var currentCamera: ErCamera
}