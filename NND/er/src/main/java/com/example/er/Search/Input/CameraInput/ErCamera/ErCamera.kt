package com.example.er.Search.Input.CameraInput.ErCamera

enum class ErCamera {
    BASE, FRONT;

    fun getCameraId(): Int {
        if (this == BASE)
            return 0
        else
            return 1
    }
}