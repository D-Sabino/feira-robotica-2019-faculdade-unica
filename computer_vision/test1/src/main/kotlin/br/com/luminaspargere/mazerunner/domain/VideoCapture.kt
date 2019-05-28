package br.com.luminaspargere.mazerunner.domain

import arrow.core.Tuple3
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte

object VideoCapture {
    fun feed(videoTransform: Mat.() -> Mat = { this }): Flow<Frame> {
        val video = VideoCapture(0)
        val frame = Mat()
        return flow {
            val isVideoClosed = !video.isOpened
            if (isVideoClosed) return@flow

            loop {
                val transformed = frame.videoTransform()
                video.read(transformed)
                emit(Frame(transformed, BufferedImage(transformed)))
            }
        }
    }

    fun detectBlueItens(): Flow<Frame> {
        return feed {

        }
    }

    @Suppress("FunctionName")
    private fun BufferedImage(frame: Mat): BufferedImage {
        val (width, height, channels) = Tuple3(frame.width(), frame.height(), frame.channels())
        val source = ByteArray(width * height * channels)
        frame.get(0, 0, source)
        val image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
        val out = (image.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(source, 0, out, 0, source.size)
        return image
    }

    data class Frame(val mat: Mat, val image: BufferedImage)
}