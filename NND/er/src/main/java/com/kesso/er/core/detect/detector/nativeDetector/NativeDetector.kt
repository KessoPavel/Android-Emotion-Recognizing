package com.kesso.er.core.detect.detector.nativeDetector

import android.app.Activity
import com.kesso.nnilib.Classifier
import com.kesso.nnilib.Device
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
        if (prediction.isNotEmpty()){
            return prediction[0].title
        }

        return ""
    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun stop() {

    }
}