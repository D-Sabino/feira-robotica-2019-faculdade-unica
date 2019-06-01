package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import java.awt.Color

@Suppress("FunctionName")
fun Mat(block: (Mat) -> Unit): Mat {
    return Mat().also(block)
}

fun hsv(h: Number, s: Number, v: Number): Scalar = Scalar(h, s, v)
fun bgr(b: Number, g: Number, r: Number): Scalar = Scalar(b, g, r)

@Suppress("FunctionName")
fun Scalar(v0: Number, v1: Number, v2: Number): Scalar {
    return Scalar(v0.toDouble(), v1.toDouble(), v2.toDouble())
}

@Suppress("FunctionName")
fun Scalar(colorHex: String): Scalar {
    val color = Color.decode(colorHex)
    return Scalar(color.blue, color.green, color.red)
}

@Suppress("FunctionName")
fun Size(width: Number, height: Number): Size {
    return Size(width.toDouble(), height.toDouble())
}

@Suppress("FunctionName")
fun Point(x: Number, y: Number): Point {
    return Point(x.toDouble(), y.toDouble())
}