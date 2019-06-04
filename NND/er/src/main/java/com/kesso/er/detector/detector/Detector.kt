package com.kesso.er.detector.detector

import com.kesso.er.detector.detector.nativeDetector.INativeDetector
import com.kesso.er.detector.input.IDetectorInput.IDetectorInput
import com.kesso.er.detector.output.IDetectorOutput
import com.kesso.mylibrary.MClassifier

class Detector(override val input: IDetectorInput,
               override val nativeDetector: INativeDetector,
               override val output: IDetectorOutput) : IDetector{
}