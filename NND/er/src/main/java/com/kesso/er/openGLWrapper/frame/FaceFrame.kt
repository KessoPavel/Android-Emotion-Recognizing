package com.kesso.er.openGLWrapper.frame

class FaceFrame(d1: Dot, d2: Dot, thickness: Float) {
    private var top: Line
    private var left: Line
    private var right: Line
    private var bottom: Line

    init {
        top = Line(d1.x, d1.y, d2.x, d1.y, thickness, Line.H)
        right = Line(d1.x, d1.y + thickness, d1.x, d2.y, thickness, Line.V)
        bottom = Line(d1.x, d2.y, d2.x, d2.y, thickness, Line.H)
        left = Line(d2.x, d1.y, d2.x, d2.y, thickness, Line.V)
    }

    fun draw(mvpMatrix: FloatArray) {
        top.draw(mvpMatrix)
        left.draw(mvpMatrix)
        bottom.draw(mvpMatrix)
        right.draw(mvpMatrix)
    }

    class Dot(var x: Float, var y: Float)
}