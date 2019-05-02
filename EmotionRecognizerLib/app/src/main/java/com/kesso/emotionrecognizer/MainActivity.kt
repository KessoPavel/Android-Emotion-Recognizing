package com.kesso.emotionrecognizer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.kesso.mylibrary.Classifier

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

const val GRAPH_FILE_PATH = "file:///android_asset/converted_model.tflite"
//const val GRAPH_FILE_PATH = "file:///android_asset/saved_model.pb"
const val LABELS_FILE_PATH = "file:///android_asset/labels.txt"

const val GRAPH_INPUT_NAME = "input_2"
const val GRAPH_OUTPUT_NAME = "softmax"

const val IMAGE_SIZE = 48
class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        var c : Classifier = Classifier.create(this, Classifier.Device.GPU, 1)
        var b = ByteArray(48*48)
        var bb = BitmapFactory.decodeByteArray(b, 0, b.size)
        var answer = c.recognizeImage(bb)
        answer.size
    }

}
