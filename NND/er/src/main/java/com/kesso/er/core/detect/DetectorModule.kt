package com.kesso.er.core.detect

import android.app.Activity
import android.content.Context
import com.kesso.er.core.detect.detector.Detector
import com.kesso.er.core.detect.detector.IDetector
import com.kesso.er.core.detect.detector.nativeDetector.INativeDetector
import com.kesso.er.core.detect.detector.nativeDetector.NativeDetector
import com.kesso.er.core.detect.input.DetectorInput
import com.kesso.er.core.detect.input.detectorInput.IDetectorInput
import com.kesso.er.core.detect.input.queueBehavior.QueueBehaviorFactory
import com.kesso.er.core.detect.output.IDetectorOutput
import com.kesso.nnilib.Device

class DetectorModule (val context: Context,
                      val activity: Activity,
                      val output: IDetectorOutput,
                      val device: Device) {

    var input: IDetectorInput? = null
    var detector: IDetector? = null
    var nativeDetector: INativeDetector? = null

    fun init(){
        val queueBehavior = QueueBehaviorFactory.createQueueBehavior(QueueBehaviorFactory.LIFO)
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