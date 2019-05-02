package com.kesso.mylibrary

import android.content.res.AssetManager

object FileUtils {
    fun getLabels(assetManager: AssetManager, labelsFilePath: String): List<String> {
        val list = ArrayList<String>()
        list += "Angry"
        list += "Disgust"
        list += "Fear"
        list += "Happy"
        list += "Sad"
        list += "Surprise"
        list += "Neutral"
        return list
    }
}