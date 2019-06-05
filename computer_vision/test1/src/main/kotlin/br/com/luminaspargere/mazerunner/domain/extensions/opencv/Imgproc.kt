package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import kotlin.math.min


/**
 * Convert the color on the given [Mat]
 * - Conversion codes on the class [Imgproc]
 */
fun Mat.convertColor(conversionCode: Int): Mat {
    return Mat { dst -> Imgproc.cvtColor(this, dst, conversionCode) }
}

fun Mat.bgr2hsv(): Mat {
    return convertColor(Imgproc.COLOR_BGR2HSV)
}

fun Mat.blurred(ksize: Size): Mat {
    return Mat { dst -> Imgproc.blur(this, dst, ksize) }
}

fun Mat.resized(width: Number, height: Number): Mat {
    return Mat { dst -> Imgproc.resize(this, dst, Size(width.toDouble(), height.toDouble())) }
}

fun Mat.morphologicalProcessing(): Mat {
    val dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(24, 24))
    val erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(12, 12))

    return Mat { dst ->
        Imgproc.erode(this, dst, erodeElement)
        Imgproc.erode(this, dst, erodeElement)

        Imgproc.dilate(this, dst, dilateElement)
        Imgproc.dilate(this, dst, dilateElement)
    }
}

fun Mat.findContours(mode: Int = Imgproc.RETR_CCOMP, method: Int = Imgproc.CHAIN_APPROX_NONE): List<Contour> {
    val contours = arrayListOf<MatOfPoint>()
    val hierarchy = Mat()
    Imgproc.findContours(this, contours, hierarchy, mode, method)
    return contours
}

fun List<Contour>.draw(dst: Mat, lineColor: Scalar, indexes: List<Int> = listOf(-1)) {
    indexes.forEach { index ->
        Imgproc.drawContours(dst, this, index, lineColor, 2)
    }
}

fun Contour.draw(dst: Mat, lineColor: Scalar, indexes: List<Int> = listOf(-1)) {
    indexes.forEach { index ->
        Imgproc.drawContours(dst, listOf(this), index, lineColor, 2)
    }
}

fun Contour.minDistance(other: Contour): Double {
    return other.toArray().fold(0.0) { acc, point ->
        val dst = MatOfPoint2f()
        this.convertTo(dst, CvType.CV_32F)
        min(acc, Imgproc.pointPolygonTest(dst, point, true))
    }
}

typealias Contour = MatOfPoint