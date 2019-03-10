package com.example.er.Search.Input.CameraInput

import com.example.er.Search.Input.BaseInput.IBaseInput

interface ICameraBaseInput: IBaseInput {
    fun getAvailableCameras():          Array<ErCamera>
    fun switchCamera(camera: ErCamera): Void
}