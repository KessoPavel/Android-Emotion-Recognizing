package com.kesso.er.core.detect.detector

import com.kesso.er.core.detect.input.detectorInput.IDetectorInput
import com.kesso.er.core.detect.input.detectorInput.IFaceListener
import com.kesso.er.core.detect.output.IDetectorOutput

interface IDetector: IFaceListener {
    val input: IDetectorInput
    val output: IDetectorOutput

    fun start()
    fun stop()
    fun pause()
    fun resume()
    val emotion: List<String>
}