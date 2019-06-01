package br.com.luminaspargere.mazerunner.domain.videomanipulation

import br.com.luminaspargere.mazerunner.domain.extensions.opencv.removeNoise
import br.com.luminaspargere.mazerunner.domain.loop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.opencv.core.Mat
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
            close()
        }.flowOn(Dispatchers.Default)
    }

    private fun open() {
        videoFeed.open(0)
    }

    fun close() {
        videoFeed.release()
    }

    companion object {
        val finalCameraOutput by lazy { CameraStream().get().map { it.removeNoise() } }
    }
}