package com.kesso.er.detector.detector.nativeDetector

interface INativeDetector {
    fun load()
    fun detect(face: ByteArray): String
}