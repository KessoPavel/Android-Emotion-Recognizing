package com.kesso.er.detector.detector

import com.kesso.er.detector.detector.nativeDetector.INativeDetector
<<<<<<< HEAD
import com.kesso.er.detector.input.IDetectorInput.IDetectorInputSearcher
import com.kesso.er.detector.output.IDatectorOutput

interface IDetector {
    val input: IDetectorInputSearcher
=======
import com.kesso.er.detector.input.IDetectorInput.IDetectorInput
import com.kesso.er.detector.input.IDetectorInput.IFaceListener
import com.kesso.er.detector.output.IDetectorOutput

interface IDetector: IFaceListener {
    val input: IDetectorInput
>>>>>>> e4830639ea0791aa3fc426aeb4955b4c8f703e6c
    val nativeDetector: INativeDetector
    val output: IDetectorOutput

    fun start()
    fun stop()
    fun pause()
    fun resume()
}