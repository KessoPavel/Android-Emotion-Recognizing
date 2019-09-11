package com.kesso.er.core.detect.detector.nativeDetector

import android.app.Activity
import com.kesso.nnilib.Classifier
import com.kesso.nnilib.Device
import com.kesso.nnilib.Recognition
import org.opencv.core.Size

class NativeDetector(
        override val activity: Activity,
        override val device: Device)
    : INativeDetector {

    override var emotionList: List<String> = emptyList()
    override val faceSize: Size = Size(64.0, 64.0)
    private lateinit var classifier: Classifier

    override fun load() {
        classifier = Classifier(
                64,
                64,
                1,
                device,
                "tf_model.tflite",
                7,
                arrayOf("Angry", "Disgust", "Fear", "Happy", "Sad", "Surprise", "Neutral"),
                PreProcessorNormalizer(),
                activity)
        emotionList = emptyList()
    }

    override fun detect(face: ByteArray): String {
        val prediction = classifier.classify(face)

        return getPrediction(prediction).title
    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun stop() {

    }

    private fun getPrediction(predictions: List<Recognition>): Recognition{
        var max: Float = 0.0f
        var answer: Recognition? = null

        for (prediction in predictions){
            if (max < prediction.confidence) {
                max = prediction.confidence
                answer = prediction
            }
        }

        return answer!!
    }
}