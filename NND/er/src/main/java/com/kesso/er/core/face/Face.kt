package com.kesso.er.core.face

import org.opencv.core.MatOfRect
import org.opencv.core.Rect

class Face(
        override val leftTopX: Int,
        override val leftTopY: Int,
        override val rightBottomX: Int,
        override val rightBottomY: Int) : IFace {
    override var emotion: String = ""

    companion object Converter {
        fun getFaces(mathOfRect: MatOfRect): List<IFace> {
            val faces = ArrayList<Face>()
            for (rect: Rect in mathOfRect.toList()) {
                val face = Face(
                        leftTopX = rect.x,
                        rightBottomX = rect.x + rect.height,
                        leftTopY = rect.y,
                        rightBottomY = rect.y + rect.width)

                faces.add(face)
            }
            return faces
        }
    }
}