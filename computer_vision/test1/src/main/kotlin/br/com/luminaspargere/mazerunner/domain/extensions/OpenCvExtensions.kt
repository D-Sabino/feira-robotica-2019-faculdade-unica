package br.com.luminaspargere.mazerunner.domain.extensions

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.awt.Color
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

fun Mat.toGrayScale(): Mat {
    return Mat().also { mat -> Imgproc.cvtColor(this, mat, Imgproc.COLOR_RGB2GRAY) }
}

fun Mat.toBinary(): Mat {
    return Mat().also { mat -> Imgproc.threshold(this, mat, 200.0, 500.0, Imgproc.THRESH_BINARY) }
}

fun Mat.flipped(): Mat {
    return Mat().also { mat -> Core.flip(this, mat, 1) }
}

fun Mat.toImage(): Image {
    val matOfByte = MatOfByte()
    Imgcodecs.imencode(".png", this, matOfByte)
    val inputStream = ByteArrayInputStream(matOfByte.toArray())
    return SwingFXUtils.toFXImage(ImageIO.read(inputStream), null)
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