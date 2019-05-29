package br.com.luminaspargere.mazerunner.domain.extensions

import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import java.awt.Color

fun Mat.toGrayScale(): Mat {
    return Mat().also { mat -> Imgproc.cvtColor(this, mat, Imgproc.COLOR_RGB2GRAY) }
}

fun Mat.toBinary(): Mat {
    return Mat().also { mat -> Imgproc.threshold(this, mat, 200.0, 500.0, Imgproc.THRESH_BINARY) }
}

@Suppress("FunctionName")
fun Scalar(r: Int, g: Int, b: Int): Scalar {
    return Scalar(b.toDouble(), g.toDouble(), r.toDouble())
}

@Suppress("FunctionName")
fun Scalar(colorHex: String): Scalar {
    val color = Color.decode(colorHex)
    return Scalar(color.red, color.green, color.blue)
}