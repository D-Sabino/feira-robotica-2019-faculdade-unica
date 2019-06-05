package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc


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

fun Mat.getContours(mode: Int = Imgproc.RETR_CCOMP, method: Int = Imgproc.CHAIN_APPROX_SIMPLE): Contours {
    val contours = arrayListOf<MatOfPoint>()
    val hierarchy = Mat()
    Imgproc.findContours(this, contours, hierarchy, mode, method)
    return Contours(hierarchy, contours)
}

fun Contours.draw(dst: Mat, lineColor: Scalar) {
    getIndexes().forEach { index ->
        Imgproc.drawContours(dst, contours, index, lineColor, 2)
    }
}

fun Contours.getIndexes(): Sequence<Int> {
    return sequence {
        if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
            var index = 0
            while (index >= 0) {
                yield(index)
                index = hierarchy.get(0, index)[0].toInt()
            }
        }
    }
}

data class Contours(val hierarchy: Mat, val contours: List<MatOfPoint>)