package com.kesso.er.detector

import android.app.Activity
import android.content.Context
import com.kesso.er.detector.output.IDetectorOutput
import com.kesso.mylibrary.MClassifier

class EmotionPredictorWrapperBuilder {
    var context: Context? = null
    var activity: Activity? = null
    var output: IDetectorOutput? = null
    var device: MClassifier.Device? = null

    fun context(context: Context) = apply { this.context = context }
    fun output(output: IDetectorOutput) = apply { this.output = output }
    fun device(device: MClassifier.Device) = apply { this.device = device }
    fun activity(activity: Activity) = apply { this.activity = activity }

    fun build(): EmotionPredictorWrapper{
        return EmotionPredictorWrapper(
                context =       context!!,
                activity =      activity!!,
                output =        output!!,
                device =        device!!)
    }
}