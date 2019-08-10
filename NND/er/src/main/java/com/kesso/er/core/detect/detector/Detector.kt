package com.kesso.er.core.detect.detector

import com.kesso.er.core.detect.detector.nativeDetector.INativeDetector
import com.kesso.er.core.detect.input.detectorInput.IDetectorInput
import com.kesso.er.core.detect.output.IDetectorOutput
import com.kesso.er.core.face.IFace
import com.kesso.er.core.frame.IBaseFrame

class Detector(override val input: IDetectorInput,
               private val nativeDetector: INativeDetector,
               override val output: IDetectorOutput)
    : IDetector{

    override val emotion: List<String>

    init {
        input.listener = this
        emotion = nativeDetector.emotionList
    }

    override fun receive(frame: IBaseFrame) {
        for (face in frame.faces){
            val emotion = nativeDetector.detect(facePreProcessing(frame, face))
            face.emotion = emotion
        }

        output.receive(frame)
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

    private fun facePreProcessing(frame: IBaseFrame, face: IFace): ByteArray {
        return ByteArray(0)
    }
}