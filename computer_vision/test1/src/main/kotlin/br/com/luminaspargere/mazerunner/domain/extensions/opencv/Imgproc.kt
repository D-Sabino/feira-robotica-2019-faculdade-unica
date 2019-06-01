package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import org.opencv.core.Mat
import org.opencv.core.Size
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

fun Mat.removeNoise(): Mat {
    return this.blurred(Size(7, 7)).bgr2hsv()
}

fun Mat.resized(width: Number, height: Number): Mat {
    return Mat { dst -> Imgproc.resize(this, dst, Size(width.toDouble(), height.toDouble())) }
}