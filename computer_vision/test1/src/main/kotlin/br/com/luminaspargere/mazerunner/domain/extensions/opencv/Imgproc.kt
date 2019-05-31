package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

fun Mat.toGrayScale(): Mat {
    return Mat { dst ->
        Imgproc.cvtColor(this, dst, Imgproc.COLOR_RGB2GRAY)
    }
}

fun Mat.toBinary(): Mat {
    return Mat { dst ->
        Imgproc.threshold(this, dst, 200.0, 500.0, Imgproc.THRESH_BINARY)
    }
}

/**
 * Conversion codes on the class [Imgproc]
 */
fun Mat.convertColor(conversionCode: Int): Mat {
    return Mat { dst -> Imgproc.cvtColor(this, dst, conversionCode) }
}