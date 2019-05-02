package com.kesso.mylibrary

import android.content.res.AssetManager
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

object ImageClassifierFactory {
    fun create(
            assetManager: AssetManager,
            graphFilePath: String,
            labelsFilePath: String,
            imageSize: Int,
            inputName: String,
            outputName: String
    ): Classifier {

        val labels = FileUtils.getLabels(assetManager, labelsFilePath)

        return ImageClassifier(
                inputName = inputName,
                outputName = outputName,
                imageSize = imageSize.toLong(),
                labels = labels,
                imageBitmapPixels = ByteArray(imageSize * imageSize),
                imageNormalizedPixels = FloatArray(imageSize * imageSize * COLOR_CHANNELS),
                results = FloatArray(labels.size),
                tensorFlowInference = TensorFlowInferenceInterface(assetManager, graphFilePath)
        )
    }
}