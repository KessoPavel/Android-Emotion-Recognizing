package com.kesso.er.detector.detector

import com.kesso.er.detector.detector.nativeDetector.INativeDetector
import com.kesso.er.detector.input.IDetectorInput.IDetectorInput
import com.kesso.er.detector.input.IDetectorInput.IFace
import com.kesso.er.detector.output.IDetectorOutput
import com.kesso.mylibrary.MClassifier

class Detector(override val input: IDetectorInput,
               override val nativeDetector: INativeDetector,
               override val output: IDetectorOutput)
    : IDetector{

    init {
        input.listener = this
    }

    override fun receive(faceList: List<IFace>) {
        for (face in faceList){
            val emotion = nativeDetector.detect(face.data)
            face.emotion = emotion
        }
        output.receive(faceList)
        input.requestData()
    }

    override fun start() {
        nativeDetector.load()
        input.requestData()
    }

    override fun stop() {
        nativeDetector.stop()
    }

    override fun resume() {
        nativeDetector.resume()
    }

    override fun pause() {
        nativeDetector.pause()
    }
}