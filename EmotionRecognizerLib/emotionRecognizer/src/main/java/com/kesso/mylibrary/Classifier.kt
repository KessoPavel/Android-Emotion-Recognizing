package com.kesso.mylibrary

import android.graphics.Bitmap

interface Classifier {
    fun recognizeImage(bitmap: Bitmap): Result
}