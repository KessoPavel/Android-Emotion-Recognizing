package com.kesso.er.Detector.input.DetectorInput

import com.kesso.er.Search.Input.BaseInput.IFrame
import org.opencv.core.MatOfRect
import org.opencv.core.Rect

class Face(
        override var data: ByteArray = ByteArray(48*48),
        override val width: Int = 48,
        override val height: Int = 48,
        override val emotion: Int = -1,
        override val frame: IFrame,
        override val x1: Int,
        override val x2: Int,
        override val y1: Int,
        override val y2: Int) : IFace {

    companion object Converter {
        fun getFaces(mathOfRect: MatOfRect, frame: IFrame): List<Face> {
            val faces = ArrayList<Face>()
            for (rect: Rect in mathOfRect.toList()) {
                val face = Face(
                        frame = frame,
                        x1 = rect.x,
                        x2 = rect.x + rect.height,
                        y1 = rect.y,
                        y2 = rect.y + rect.width)

                faces.add(face)
            }
            return faces
        }
    }
}