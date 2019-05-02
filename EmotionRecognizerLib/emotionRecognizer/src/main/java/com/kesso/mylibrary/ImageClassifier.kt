package com.kesso.mylibrary

import android.graphics.Bitmap
import java.util.*

private const val ENABLE_LOG_STATS = false
const val COLOR_CHANNELS = 3;

class ImageClassifier (
        private val inputName: String,
        private val outputName: String,
        private val imageSize: Long,
        private val labels: List<String>,
        private val imageBitmapPixels: ByteArray,
        private val imageNormalizedPixels: FloatArray,
        private val results: FloatArray,
        private val tensorFlowInference: TensorFlowInferenceInterface
) : Classifier {

    override fun recognizeImage(bitmap: Bitmap): Result {
        preprocessImageToNormalizedFloats(bitmap)
        classifyImageToOutputs()
        val outputQueue = getResults()
        return outputQueue.poll()
    }

    private fun preprocessImageToNormalizedFloats(bitmap: Bitmap) {
        // Preprocess the image data from 0-255 int to normalized float based
        // on the provided parameters.
    }

    private fun classifyImageToOutputs() {
        //feed the classifier with the data via input
        tensorFlowInference.feed(inputName, imageNormalizedPixels,
                1L, imageSize, imageSize, COLOR_CHANNELS.toLong())
        //run the classification
        tensorFlowInference.run(arrayOf(outputName), ENABLE_LOG_STATS)
        //get the results from the ouptput
        tensorFlowInference.fetch(outputName, results)
    }

    private fun getResults(): PriorityQueue<Result> {
        val outputQueue = createOutputQueue()
        results.indices.mapTo(outputQueue) { Result(labels[it], results[it]) }
        return outputQueue
    }

    private fun createOutputQueue(): PriorityQueue<Result> {
        val comp = Comparator<Result> { rConfidence, lConfidence ->
            (rConfidence.confidence - lConfidence.confidence).toInt()
        }
        return PriorityQueue(
                labels.size,
                comp)
    }
}