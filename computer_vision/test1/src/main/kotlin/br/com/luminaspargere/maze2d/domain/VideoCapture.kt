package br.com.luminaspargere.maze2d.domain

import arrow.core.Tuple3
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte

object VideoCapture {
    fun feed(cameraIp: String = "http://192.168.1.4:8080/?action=stream"): Flow<BufferedImage> {
        val video = VideoCapture(cameraIp)
        val frame = Mat()
        return flow {
            val isVideoClosed = !video.isOpened
            println("closed? $isVideoClosed")
            if (isVideoClosed) return@flow

            loop {
                video.read(frame)
                emit(BufferedImage(frame))
            }
        }
    }

    @Suppress("FunctionName")
    private fun BufferedImage(frame: Mat): BufferedImage {
        val (width, height, channels) = Tuple3(frame.width(), frame.height(), frame.channels())
        val source = ByteArray(width * height * channels)
        frame[0, 0, source]
        val image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
        val out = (image.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(source, 0, out, 0, source.size)
        return image
    }
}