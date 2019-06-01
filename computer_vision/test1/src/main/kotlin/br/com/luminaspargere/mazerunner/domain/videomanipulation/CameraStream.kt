package br.com.luminaspargere.mazerunner.domain.videomanipulation

import arrow.core.Try
import arrow.core.toT
import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.hsv
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.resized
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.threshHoldColorRanges
import br.com.luminaspargere.mazerunner.domain.loop
import br.com.luminaspargere.mazerunner.presentation.main.MainView
import javafx.beans.property.IntegerProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.videoio.VideoCapture


class CameraStream {
    private val videoFeed by lazy { VideoCapture() }

    fun get(): Flow<Mat> {
        open()
        val frame = Mat()
        return flow {
            if (videoFeed.isOpened) loop {
                videoFeed.read(frame)
                emit(frame)
            }
            Try { close() }
        }.flowOn(Dispatchers.Default)
    }

    private fun open() {
        videoFeed.open(0)
    }

    fun close() {
        videoFeed.release()
    }

    companion object {
        private val stream by lazy { CameraStream().get() }
        val finalCameraOutput by lazy {
            stream.map {
                it.resized(MainView.IMAGES_WIDTH / 2, MainView.IMAGES_HEIGHT)
            }
        }

        val robotTrackingOutput by lazy {
            stream.map {
                val start = hsv(Config.srcHueStart, Config.srcSatStart, Config.srcValueStart)
                val end = hsv(Config.srcHueEnd, Config.srcSatEnd, Config.srcValueEnd)
                it.threshHoldColorRanges(start toT end)
                        .resized(MainView.IMAGES_WIDTH / 2, MainView.IMAGES_HEIGHT / 2)
            }
        }

        val targetTrackingOutput by lazy {
            stream.map {
                val start = hsv(Config.dstHueStart, Config.dstSatStart, Config.dstValueStart)
                val end = hsv(Config.dstHueEnd, Config.dstSatEnd, Config.dstValueEnd)
                it.threshHoldColorRanges(start toT end)
                        .resized(MainView.IMAGES_WIDTH / 2, MainView.IMAGES_HEIGHT / 2)
            }
        }

        private fun hsv(h: IntegerProperty, s: IntegerProperty, v: IntegerProperty): Scalar {
            return hsv(h.get(), s.get(), v.get())
        }
    }
}