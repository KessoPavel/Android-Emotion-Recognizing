package com.kesso.mylibrary;

import android.app.Activity;

import java.io.IOException;

public class TFModel extends MClassifier {
    private float[][] labelProbArray = null;


    public TFModel(Activity activity, Device device, int numThreads) throws IOException {
        super(activity, device, numThreads);
        labelProbArray = new float[1][getNumLabels()];
    }

    @Override
    public int getImageSizeX() {
        return 64;
    }

    @Override
    public int getImageSizeY() {
        return 64;
    }

    @Override
    protected String getModelPath() {
        return "tf_model.tflite";
    }

    @Override
    protected String getLabelPath() {
        return "labels.txt";
    }

    @Override
    protected int getNumBytesPerChannel() {
        return 1;
    }

    @Override
    protected void addPixelValue(int pixelValue) {
        float f = (float) pixelValue;
        f /= 255;
        f -= 0.5;
        f *= 2;
        imgData.putFloat(f);
    }

    @Override
    protected float getProbability(int labelIndex) {
        return labelProbArray[0][labelIndex];
    }

    @Override
    protected void setProbability(int labelIndex, Number value) {
        labelProbArray[0][labelIndex] = value.floatValue();
    }

    @Override
    protected float getNormalizedProbability(int labelIndex) {
        return labelProbArray[0][labelIndex];
    }

    @Override
    protected void runInference() {
        tflite.run(imgData, labelProbArray);
    }
}
