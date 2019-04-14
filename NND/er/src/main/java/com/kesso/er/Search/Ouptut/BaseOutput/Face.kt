package com.kesso.er.Search.Ouptut.BaseOutput

import org.opencv.core.MatOfRect
import org.opencv.core.Rect

class Face {
    var x1: Int = -1
    var y1: Int = -1
    var x2: Int = -1
    var y2: Int = -1

    companion object Converter {
        fun getFaces(mathOfRect: MatOfRect): List<Face> {
            val faces = ArrayList<Face>();
            for (rect: Rect in mathOfRect.toList()) {
                val face = Face()
                face.x1 = rect.x
                face.y1 = rect.y
                face.x2 = rect.x + rect.height
                face.y2 = rect.y + rect.width

                faces.add(face)
            }
            return faces
        }
    }
}