package com.kesso.er.detector.detector

import com.kesso.er.detector.detector.nativeDetector.INativeDetector
import com.kesso.er.detector.input.IDetectorInput.IDetectorInput
import com.kesso.er.detector.output.IDetectorOutput

interface IDetector {
    val input: IDetectorInput
    val nativeDetector: INativeDetector
    val output: IDetectorOutput
}