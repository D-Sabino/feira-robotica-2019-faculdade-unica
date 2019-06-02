package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import arrow.core.Tuple2
import arrow.core.toT
import br.com.luminaspargere.mazerunner.domain.Config
import javafx.beans.property.IntegerProperty
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

fun Mat.markRobotAndTarget(): Mat {
    fun hsv(h: IntegerProperty, s: IntegerProperty, v: IntegerProperty): Scalar {
        return hsv(h.get(), s.get(), v.get())
    }

    val robotStart = hsv(Config.srcHueStart, Config.srcSatStart, Config.srcValueStart)
    val robotEnd = hsv(Config.srcHueEnd, Config.srcSatEnd, Config.srcValueEnd)

    val targetStart = hsv(Config.dstHueStart, Config.dstSatStart, Config.dstValueStart)
    val targetEnd = hsv(Config.dstHueEnd, Config.dstSatEnd, Config.dstValueEnd)

    return this
            .markObjectsOnColorRange(robotStart toT robotEnd, Scalar("#0000ff"))
            .markObjectsOnColorRange(targetStart toT targetEnd, Scalar("#ff0000"))
}

fun Mat.markObjectsOnColorRange(range: Tuple2<Scalar, Scalar>, markingColor: Scalar): Mat {
    val (start, end) = range
    return this
            .blurred(Size(7, 7))
            .bgr2hsv()
            .thresholdColorRanges(start toT end)
            .morphologicalProcessing()
            .trackObjects(this, markingColor)
}

private fun Mat.morphologicalProcessing(): Mat {
    val dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(24, 24))
    val erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(12, 12))

    return Mat { dst ->
        Imgproc.erode(this, dst, erodeElement)
        Imgproc.erode(this, dst, erodeElement)

        Imgproc.dilate(this, dst, dilateElement)
        Imgproc.dilate(this, dst, dilateElement)
    }
}

private fun Mat.trackObjects(originalImage: Mat, lineColor: Scalar): Mat {
    val contours = arrayListOf<MatOfPoint>()
    val hierarchy = Mat()

    Imgproc.findContours(this, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE)
    if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
        var idx = 0
        while (idx >= 0) {
            Imgproc.drawContours(originalImage, contours, idx, lineColor, 2)
            idx = hierarchy.get(0, idx)[0].toInt()
        }
    }
    return originalImage
}