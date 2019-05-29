package br.com.luminaspargere.mazerunner.domain

import br.com.luminaspargere.mazerunner.domain.extensions.Scalar
import br.com.luminaspargere.mazerunner.domain.extensions.toGrayScale
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO


object VideoCapture {
    operator fun invoke() = detectLines()

    fun original(videoTransform: Mat.() -> Mat = { this }): Flow<Frame> {
        val video = VideoCapture(0)
        val frame = Mat()
        return flow {
            val isVideoClosed = !video.isOpened
            if (isVideoClosed) return@flow

            loop {
                video.read(frame)
                val transformed = videoTransform(frame)
                emit(Frame(transformed, transformed.image()))
            }
        }
    }

    fun detectLines(): Flow<Frame> = original {
        val src = this.toGrayScale()
        val dst = Mat()
        val cdst = Mat()

        Imgproc.Canny(src, dst, 50.0, 200.0, 3, false)
        Imgproc.cvtColor(dst, cdst, Imgproc.COLOR_GRAY2BGR)

        val cdstP = cdst.clone()
        val lines = Mat()
        Imgproc.HoughLines(dst, lines, 1.0, Math.PI / 180, 150)

        for (line in 0 until lines.rows()) {
            val rho = lines.get(line, 0)[0]
            val theta = lines.get(line, 0)[1]
            val a = Math.cos(theta)
            val b = Math.sin(theta)
            val x0 = a * rho
            val y0 = b * rho
            val pt1 = Point(Math.round(x0 + 1000 * -b).toDouble(), Math.round(y0 + 1000 * a).toDouble())
            val pt2 = Point(Math.round(x0 - 1000 * -b).toDouble(), Math.round(y0 - 1000 * a).toDouble())
            Imgproc.line(cdst, pt1, pt2, Scalar(0.0, 0.0, 255.0), 3, Imgproc.LINE_AA, 0)
        }

        val linesP = Mat()
        Imgproc.HoughLinesP(dst, linesP, 1.0, Math.PI / 180, 50, 50.0, 10.0)
        for (line in 0 until linesP.rows()) {
            val l = linesP.get(line, 0)
            Imgproc.line(cdstP, Point(l[0], l[1]), Point(l[2], l[3]), Scalar("#0000ff"), 3, Imgproc.LINE_AA, 0)
        }

        cdstP
    }

//    fun detectBlueItens(): Flow<Frame> {
//        return original {
//
//        }
//    }


    private fun Mat.image(): Image {
        val matOfByte = MatOfByte()
        Imgcodecs.imencode(".png", this, matOfByte)
        val inputStream = ByteArrayInputStream(matOfByte.toArray())
        return SwingFXUtils.toFXImage(ImageIO.read(inputStream), null)
    }

    data class Frame(val mat: Mat, val image: Image)
}