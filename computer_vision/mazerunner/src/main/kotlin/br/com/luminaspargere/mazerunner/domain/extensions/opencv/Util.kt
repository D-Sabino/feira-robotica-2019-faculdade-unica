@file:Suppress("FunctionName")

package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import java.awt.Color

fun hsv(h: Number, s: Number, v: Number): Scalar = Scalar(h, s, v)
fun bgr(b: Number, g: Number, r: Number): Scalar = Scalar(b, g, r)

fun Mat(block: (Mat) -> Unit): Mat {
    return Mat().also(block)
}

fun Scalar(v0: Number, v1: Number, v2: Number): Scalar {
    return Scalar(v0.toDouble(), v1.toDouble(), v2.toDouble())
}

fun Scalar(colorHex: String): Scalar {
    return Scalar(Color.decode(colorHex))
}

fun Scalar(color: Color): Scalar {
    return Scalar(color.blue, color.green, color.red)
}

fun Size(width: Number, height: Number): Size {
    return Size(width.toDouble(), height.toDouble())
}

fun Point(x: Number, y: Number): Point {
    return Point(x.toDouble(), y.toDouble())
}