package br.com.luminaspargere.mazerunner.domain.extensions.opencv.tracking

import arrow.core.Try
import arrow.core.Tuple3
import br.com.luminaspargere.mazerunner.data.repository.ArduinoControlRepository
import br.com.luminaspargere.mazerunner.domain.Injector
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.minDistance
import com.github.icarohs7.unoxcore.extensions.coroutines.forEach
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.inject
import java.lang.Math.abs

object ObjectTracking : CoroutineScope by GlobalScope {
    private val arduinoControlRepository: ArduinoControlRepository by Injector.inject()
    private val contoursChannel = Channel<Tuple3<TrackedFrame, TrackedFrame, TrackedFrame>>(1)

    init {
        launch {
            contoursChannel.forEach {
                val timer = async { delay(200) }
                Try { processFrame(it) }
                timer.await()
            }
        }
    }

    fun send(srcTipFrame: TrackedFrame, srcFrame: TrackedFrame, dstFrame: TrackedFrame) {
        contoursChannel.offer(Tuple3(srcTipFrame, srcFrame, dstFrame))
    }

    private suspend fun processFrame(frames: Tuple3<TrackedFrame, TrackedFrame, TrackedFrame>) {
        val distance = { a: TrackedFrame, b: TrackedFrame -> abs(a.contours[0].minDistance(b.contours[0])) }

        val (tip, src, dst) = frames
        val tip2Dst = distance(tip, dst)
        val src2Dst = distance(src, dst)

        if (src2Dst > 50) {
            println("src2dst => $src2Dst")
            println("tip2dst => $tip2Dst")
            if (tip2Dst > (src2Dst + 25)) arduinoControlRepository.turnRight()
            else arduinoControlRepository.goForward()
        }
    }
}