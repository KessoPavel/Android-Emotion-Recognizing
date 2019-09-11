package com.kesso.er.core.detect.detector.nativeDetector

import com.kesso.nnilib.PreProcessor

class PreProcessorNormalizer: PreProcessor {
    override fun preProcessing(pixel: Byte): Float {
        return pixel.toFloat() / 255.0f
    }
}