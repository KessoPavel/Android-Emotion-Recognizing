package com.kesso.er.detector

import android.app.Activity
import android.content.Context
import com.kesso.er.detector.detector.Detector
import com.kesso.er.detector.detector.IDetector
import com.kesso.er.detector.detector.nativeDetector.INativeDetector
import com.kesso.er.detector.detector.nativeDetector.NativeDetector
import com.kesso.er.detector.input.DetectorInput
import com.kesso.er.detector.input.IDetectorInput.IDetectorInput
import com.kesso.er.detector.input.QueueBehavior.QueueBehaviorFactory
import com.kesso.er.detector.output.IDetectorOutput
import com.kesso.mylibrary.EmotionClassifier

class EmotionPredictorWrapper (val context: Context,
                               val activity: Activity,
                               val output: IDetectorOutput,
                               val device: EmotionClassifier.Device) {
    var input: IDetectorInput? = null
    var detector: IDetector? = null
    var nativeDetector: INativeDetector? = null

    fun init(){
        var queueBehavior = QueueBehaviorFactory.createQueueBehavior(QueueBehaviorFactory.LIFO)
        input = DetectorInput(queueBehavior)

        nativeDetector = NativeDetector(activity, device)
        detector = Detector(input!!, nativeDetector!!, output)
    }

    fun open(){
        val r = Runnable {
            detector?.start()
        }
        val t = Thread(r)
        t.start()
    }

    fun close(){
        detector?.stop()
    }
}