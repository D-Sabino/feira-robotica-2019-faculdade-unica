package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import arrow.core.Tuple2
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

operator fun Mat.plus(other: Mat): Mat {
    return Mat { dst -> Core.bitwise_or(this, other, dst) }
}

operator fun Mat.times(other: Mat): Mat {
    return Mat { dst -> Core.bitwise_and(this, other, dst) }
}

fun Mat.flipped(): Mat {
    return Mat { dst -> Core.flip(this, dst, 1) }
}

fun Mat.threshHoldColorRanges(vararg ranges: Tuple2<Scalar, Scalar>): Mat {
    val hsv = this.flipped().convertColor(Imgproc.COLOR_BGR2HSV)
    return ranges.map { (from, to) ->
        Mat { dst -> Core.inRange(hsv, from, to, dst) }
    }.reduce(Mat::plus)
}