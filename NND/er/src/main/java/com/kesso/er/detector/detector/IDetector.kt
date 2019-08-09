package com.kesso.er.detector.detector

import com.kesso.er.detector.detector.nativeDetector.INativeDetector
import com.kesso.er.detector.input.IDetectorInput.IDetectorInputSearcher
import com.kesso.er.detector.output.IDatectorOutput

interface IDetector {
    val input: IDetectorInputSearcher
    val nativeDetector: INativeDetector
    val output: IDatectorOutput
}