package com.kesso.er.core.detect.detector

import com.kesso.er.core.detect.detector.nativeDetector.INativeDetector
import com.kesso.er.core.detect.input.IDetectorInput.IDetectorInput
import com.kesso.er.core.detect.input.IDetectorInput.IFace
import com.kesso.er.core.detect.output.IDetectorOutput

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