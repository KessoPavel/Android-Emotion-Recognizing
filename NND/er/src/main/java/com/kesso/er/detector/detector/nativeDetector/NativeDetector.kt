package com.kesso.er.detector.detector.nativeDetector

import android.app.Activity
import com.kesso.mylibrary.MClassifier

class NativeDetector(
        override val activity: Activity,
        override val device: MClassifier.Device)
    : INativeDetector {

    private lateinit var classifier: MClassifier

    override fun load() {
        classifier = MClassifier.create(activity, MClassifier.Device.CPU, 1, MClassifier.Model.TFModel)
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