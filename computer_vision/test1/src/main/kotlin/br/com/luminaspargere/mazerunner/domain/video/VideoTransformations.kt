package br.com.luminaspargere.mazerunner.domain.video

import arrow.core.toT
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.Scalar
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.hsv
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.thresholdColorRanges
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.toGrayScale
import br.com.luminaspargere.mazerunner.presentation._communication.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

fun Flow<Mat>.detectLines(): Flow<Mat> = this.map { frame ->
    val src = frame.toGrayScale()
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
        Imgproc.line(cdstP, Point(l[0], l[1]), Point(l[2], l[3]),
                     Scalar("#0000ff"), 3, Imgproc.LINE_AA, 0)
    }

    cdstP
}

fun Flow<Mat>.hideForegroundColor(): Flow<Mat> = this.map { frame ->
    frame.thresholdColorRanges(
            hsv(0, 120, 70) toT hsv(10, 255, 255),
            hsv(170, 120, 70) toT hsv(180, 255, 255)
    )
}

private fun show(text: String) = Messages.i(text)