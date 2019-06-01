package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import java.awt.Color

@Suppress("FunctionName")
fun Mat(block: (Mat) -> Unit): Mat {
    return Mat().also(block)
}

fun hsv(h: Int, s: Int, v: Int): Scalar = Scalar(h, s, v)
fun bgr(b: Int, g: Int, r: Int): Scalar = Scalar(b, g, r)

@Suppress("FunctionName")
fun Scalar(v0: Int, v1: Int, v2: Int): Scalar {
    return Scalar(v0.toDouble(), v1.toDouble(), v2.toDouble())
}

@Suppress("FunctionName")
fun Scalar(colorHex: String): Scalar {
    val color = Color.decode(colorHex)
    return Scalar(color.blue, color.green, color.red)
}

@Suppress("FunctionName")
fun Size(width: Int, height: Int): Size {
    return Size(width.toDouble(), height.toDouble())
}