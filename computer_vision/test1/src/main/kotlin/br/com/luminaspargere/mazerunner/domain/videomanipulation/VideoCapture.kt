package br.com.luminaspargere.mazerunner.domain.videomanipulation

import br.com.luminaspargere.mazerunner.domain.loop
import com.github.icarohs7.unoxcore.extensions.coroutines.dispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import kotlin.coroutines.coroutineContext


class VideoCapture {
    private val videoFeed by lazy { VideoCapture() }

    fun cameraImageFeed(): Flow<Mat> {
        open()
        val frame = Mat()
        return flow {
            println("Hi from ${coroutineContext.dispatcher}")
            if (videoFeed.isOpened) loop {
                videoFeed.read(frame)
                emit(frame)
            }
        }
    }

    fun open() {
        videoFeed.open(0)
    }

    fun close() {
        videoFeed.release()
    }
}