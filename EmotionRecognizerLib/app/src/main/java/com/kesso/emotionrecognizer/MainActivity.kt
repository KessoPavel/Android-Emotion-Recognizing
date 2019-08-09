package com.kesso.emotionrecognizer

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.kesso.mylibrary.EmotionClassifier
import kotlinx.android.synthetic.main.activity_main.*

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

        var c : EmotionClassifier = EmotionClassifier.create(this, EmotionClassifier.Device.CPU, 1, EmotionClassifier.Model.TFModel)
        var b = ByteArray(64*64)
        var answer = c.recognizeImage(b)
        answer.size
    }

}
