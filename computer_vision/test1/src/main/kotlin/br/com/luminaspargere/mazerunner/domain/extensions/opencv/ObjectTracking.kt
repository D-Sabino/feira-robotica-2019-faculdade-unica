package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import arrow.core.Tuple2
import arrow.core.toT
import br.com.luminaspargere.mazerunner.domain.Config
import javafx.beans.property.IntegerProperty
import org.opencv.core.Mat
import org.opencv.core.Scalar

fun Mat.activateObjectsTracking(): Mat {
    fun hsv(h: IntegerProperty, s: IntegerProperty, v: IntegerProperty): Scalar {
        return hsv(h.get(), s.get(), v.get())
    }

    val robotTipStart = hsv(Config.srcTipHueStart, Config.srcTipSatStart, Config.srcTipValueStart)
    val robotTipEnd = hsv(Config.srcTipHueEnd, Config.srcTipSatEnd, Config.srcTipValueEnd)

    val robotStart = hsv(Config.srcHueStart, Config.srcSatStart, Config.srcValueStart)
    val robotEnd = hsv(Config.srcHueEnd, Config.srcSatEnd, Config.srcValueEnd)

    val targetStart = hsv(Config.dstHueStart, Config.dstSatStart, Config.dstValueStart)
    val targetEnd = hsv(Config.dstHueEnd, Config.dstSatEnd, Config.dstValueEnd)

    val tipTracking = trackObjects(robotTipStart toT robotTipEnd)
    val srcTracking = trackObjects(robotStart toT robotEnd)
    val dstTracking = trackObjects(targetStart toT targetEnd)

    return apply {
        tipTracking.contours.draw(this, Scalar("#ff0000"))
        srcTracking.contours.draw(this, Scalar("#000000"))
        dstTracking.contours.draw(this, Scalar("#ffff00"))
    }
}

private fun Mat.trackObjects(range: Tuple2<Scalar, Scalar>): TrackedFrame {
    val (start, end) = range
    val filtered = this
            .blurred(Size(7, 7))
            .bgr2hsv()
            .thresholdColorRanges(start toT end)
            .morphologicalProcessing()

    val contours = filtered.getContours()
    return TrackedFrame(filtered, this, contours)
}

data class TrackedFrame(val filteredFrame: Mat, val destinationFrame: Mat, val contours: Contours)