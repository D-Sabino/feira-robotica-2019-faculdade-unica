package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import org.opencv.videoio.VideoCapture

object OpenCvUtils {
    suspend fun openCameraIndexes(): List<Int> {
        return onBackground {
            sequence {
                (0..100).forEach { num ->
                    val cap = VideoCapture(num)
                    if (cap.isOpened) yield(num)
                    cap.release()
                }
            }.toList()
        }
    }
}