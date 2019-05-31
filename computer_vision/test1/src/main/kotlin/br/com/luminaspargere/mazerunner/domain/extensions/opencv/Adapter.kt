package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

fun Mat.toImage(): Image {
    val matOfByte = MatOfByte()
    Imgcodecs.imencode(".png", this, matOfByte)
    val inputStream = ByteArrayInputStream(matOfByte.toArray())
    return SwingFXUtils.toFXImage(ImageIO.read(inputStream), null)
}