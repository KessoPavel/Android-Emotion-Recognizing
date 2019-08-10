package com.kesso.er.core.detect.detector.nativeDetector

import android.app.Activity
import com.kesso.mylibrary.EmotionClassifier

class NativeDetector(
        override val activity: Activity,
        override val device: EmotionClassifier.Device)
    : INativeDetector {

    override var emotionList: List<String> = emptyList()
    private lateinit var classifier: EmotionClassifier

    override fun load() {
        classifier = EmotionClassifier.create(activity, EmotionClassifier.Device.CPU, 1, EmotionClassifier.Model.TFModel)
        emotionList = emptyList()
    }

    override fun detect(face: ByteArray): String {
        val prediction = classifier.recognizeImage(face)
        if (prediction.size > 0){
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