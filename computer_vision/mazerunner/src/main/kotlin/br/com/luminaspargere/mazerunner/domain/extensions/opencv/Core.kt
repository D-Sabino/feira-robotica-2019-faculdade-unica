package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import arrow.core.Tuple2
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar

operator fun Mat.plus(other: Mat): Mat {
    return Mat { dst -> Core.bitwise_or(this, other, dst) }
}

operator fun Mat.times(other: Mat): Mat {
    return Mat { dst -> Core.bitwise_and(this, other, dst) }
}

fun Mat.flipped(): Mat {
    return Mat { dst -> Core.flip(this, dst, 1) }
}

fun Mat.thresholdColorRanges(vararg ranges: Tuple2<Scalar, Scalar>): Mat {
    return ranges.map { (from, to) ->
        Mat { dst -> Core.inRange(this, from, to, dst) }
    }.reduce(Mat::plus)
}