package br.com.luminaspargere.mazerunner.domain.video

import arrow.core.Try
import br.com.luminaspargere.mazerunner.domain.loop
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.broadcast
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture


@Suppress("ObjectPropertyName")
object CameraStream {
    private val videoFeed by lazy { VideoCapture() }
    private val _channel by lazy {
        GlobalScope.broadcast {
            val frame = Mat()
            videoFeed.open(0)
            if (videoFeed.isOpened) loop {
                Try { videoFeed.read(frame) }
                offer(frame)
            }
        }
    }

    val channel: ReceiveChannel<Mat> by lazy { _channel.openSubscription() }
}