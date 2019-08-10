package com.kesso.er.core.detector

import android.app.Activity
import android.content.Context
import com.kesso.er.core.detector.output.IDetectorOutput
import com.kesso.mylibrary.EmotionClassifier

class EmotionPredictorWrapperBuilder {
    var context: Context? = null
    var activity: Activity? = null
    var output: IDetectorOutput? = null
    var device: EmotionClassifier.Device? = null

    fun context(context: Context) = apply { this.context = context }
    fun output(output: IDetectorOutput) = apply { this.output = output }
    fun device(device: EmotionClassifier.Device) = apply { this.device = device }
    fun activity(activity: Activity) = apply { this.activity = activity }

    fun build(): EmotionPredictorWrapper{
        return EmotionPredictorWrapper(
                context =       context!!,
                activity =      activity!!,
                output =        output!!,
                device =        device!!)
    }
}