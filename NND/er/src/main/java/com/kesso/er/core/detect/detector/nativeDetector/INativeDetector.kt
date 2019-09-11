package com.kesso.er.core.detect.detector.nativeDetector

import android.app.Activity
import com.kesso.nnilib.Device
import org.opencv.core.Size

interface INativeDetector {
    val activity: Activity
    val device: Device
    val emotionList: List<String>
    val faceSize: Size

    fun load()
    fun detect(face: ByteArray): String
    fun stop()
    fun resume()
    fun pause()
}