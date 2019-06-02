package br.com.luminaspargere.mazerunner.domain.video

import arrow.core.toT
import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.Mat
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.Size
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.resized
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.threshHoldColorRanges
import br.com.luminaspargere.mazerunner.presentation.main.MainView
import javafx.beans.property.IntegerProperty
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

object ProcessedOutputs {
    fun originalOutput(): ReceiveChannel<Mat> = CameraStream.channel.map { src ->
        src.resized(MainView.IMAGES_WIDTH / 2, MainView.IMAGES_HEIGHT)
    }

    fun robotTrackingOutput(): ReceiveChannel<Mat> = CameraStream.channel.map { src ->
        val start = hsv(Config.srcHueStart, Config.srcSatStart, Config.srcValueStart)
        val end = hsv(Config.srcHueEnd, Config.srcSatEnd, Config.srcValueEnd)
        src.resized(MainView.IMAGES_WIDTH / 2, MainView.IMAGES_HEIGHT / 2)
                .threshHoldColorRanges(start toT end)
                .morphologicalProcessing()
                .trackObjects()
    }

    fun targetTrackingOutput(): ReceiveChannel<Mat> = CameraStream.channel.map { src ->
        val start = hsv(Config.dstHueStart, Config.dstSatStart, Config.dstValueStart)
        val end = hsv(Config.dstHueEnd, Config.dstSatEnd, Config.dstValueEnd)
        src.resized(MainView.IMAGES_WIDTH / 2, MainView.IMAGES_HEIGHT / 2)
                .threshHoldColorRanges(start toT end)
                .morphologicalProcessing()
                .trackObjects()
    }

    private fun hsv(h: IntegerProperty, s: IntegerProperty, v: IntegerProperty): Scalar {
        return br.com.luminaspargere.mazerunner.domain.extensions.opencv.hsv(h.get(), s.get(), v.get())
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

    private fun Mat.trackObjects(): Mat {
        val contours = arrayListOf<MatOfPoint>()
        val hierarchy = Mat()

        Imgproc.findContours(this, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE)
        return Mat { dst ->
            copyTo(dst)
            //                if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
//                    var idx = 0
//                    while (idx >= 0) {
//                        Imgproc.drawContours(dst, contours, idx, Scalar("#0000ff"))
//                        idx = hierarchy.get(0, idx)[0].toInt()
//                    }
//                } else {
//                    this.copyTo(dst)
//                }
        }
    }
}