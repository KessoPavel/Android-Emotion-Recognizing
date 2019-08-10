package com.kesso.er.core.detect.detector

import com.kesso.er.core.detect.detector.nativeDetector.INativeDetector
import com.kesso.er.core.detect.input.detectorInput.IDetectorInput
import com.kesso.er.core.detect.output.IDetectorOutput
import com.kesso.er.core.face.IFace
import com.kesso.er.core.frame.IBaseFrame
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Detector(override val input: IDetectorInput,
               private val nativeDetector: INativeDetector,
               override val output: IDetectorOutput)
    : IDetector{

    override val emotion: List<String>
    private val faceSize: Size

    init {
        input.listener = this
        emotion = nativeDetector.emotionList
        faceSize = nativeDetector.faceSize
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
        val rect = Rect(
                face.leftTopX,
                face.leftTopY,
                face.rightBottomX - face.leftTopX,
                face.rightBottomY - face.leftTopY)

        val crop = Mat(frame.data, rect)
        val resize = Mat()
        Imgproc.resize(crop, resize, faceSize)
        val cropFace = ByteArray((faceSize.height * faceSize.width).toInt())

        resize.get(0, 0, cropFace)

        return cropFace
    }
}