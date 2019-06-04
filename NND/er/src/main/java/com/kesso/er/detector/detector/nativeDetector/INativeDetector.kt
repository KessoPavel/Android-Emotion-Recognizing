package com.kesso.er.detector.detector.nativeDetector

import android.app.Activity
import com.kesso.mylibrary.MClassifier

interface INativeDetector {
    val activity: Activity
    val device: MClassifier.Device

    fun load()
    fun detect(face: ByteArray): String
    fun stop()
    fun resume()
    fun pause()
}