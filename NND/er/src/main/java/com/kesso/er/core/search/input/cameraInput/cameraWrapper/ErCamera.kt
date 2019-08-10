package com.kesso.er.core.search.input.cameraInput.cameraWrapper

enum class ErCamera {
    BASE, FRONT;

    fun getCameraId(): Int {
        if (this == BASE)
            return 0
        else
            return 1
    }
}