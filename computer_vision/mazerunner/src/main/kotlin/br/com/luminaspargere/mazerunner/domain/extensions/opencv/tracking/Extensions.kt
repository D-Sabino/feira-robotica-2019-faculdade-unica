package br.com.luminaspargere.mazerunner.domain.extensions.opencv.tracking

import arrow.core.Tuple2
import arrow.core.toT
import br.com.luminaspargere.mazerunner.data.repository.ArduinoControlRepository
import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.Injector
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.Scalar
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.bgr2hsv
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.draw
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.findContours
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.morphologicalProcessing
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.thresholdColorRanges
import javafx.beans.property.IntegerProperty
import org.koin.core.inject
import org.opencv.core.Mat
import org.opencv.core.Scalar

private val arduinoControlRepository: ArduinoControlRepository by Injector.inject()

fun Mat.activateObjectsTrackingAndControlRobot(): Mat {
    if (!ObjectTracking.isActive) {
        ObjectTracking.sendNoOp()
        return this
    }

    fun hsv(h: IntegerProperty, s: IntegerProperty, v: IntegerProperty): Scalar {
        return br.com.luminaspargere.mazerunner.domain.extensions.opencv.hsv(h.get(), s.get(), v.get())
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

    ObjectTracking.send(tipTracking, srcTracking, dstTracking)

    return apply {
        tipTracking.contours.draw(this, Scalar("#00ff00"))
        srcTracking.contours.draw(this, Scalar("#ffff44"))
        dstTracking.contours.draw(this, Scalar("#ff4444"))
    }
}

private fun Mat.trackObjects(range: Tuple2<Scalar, Scalar>): TrackedFrame {
    val (start, end) = range
    val filtered = this
            .bgr2hsv()
            .thresholdColorRanges(start toT end)
            .morphologicalProcessing()

    val contours = filtered.findContours()
    return TrackedFrame(filtered, this, contours)
}