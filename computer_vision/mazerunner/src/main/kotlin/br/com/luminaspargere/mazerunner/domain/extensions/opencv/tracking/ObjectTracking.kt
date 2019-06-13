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
import kotlin.math.absoluteValue

object ObjectTracking : CoroutineScope by GlobalScope {
    private val noOpChannel = Channel<Unit>(1)
    private val arduinoControlRepository: ArduinoControlRepository by Injector.inject()
    private val contoursChannel = Channel<Tuple3<TrackedFrame, TrackedFrame, TrackedFrame>>(1)
    var isActive = false

    init {
        launch {
            contoursChannel.forEach {
                val timer = async { delay(300) }
                val res = Try { processFrame(it) }
                if (res.isFailure()) sendNoOp()
                timer.await()
            }
        }

        launch {
            noOpChannel.forEach {
                val t = GlobalScope.async { delay(400) }
                arduinoControlRepository.turnLeft()
                arduinoControlRepository.turnRight()
                t.await()
            }
        }
    }

    fun sendNoOp() {
        noOpChannel.offer(Unit)
    }

    fun send(srcTipFrame: TrackedFrame, srcFrame: TrackedFrame, dstFrame: TrackedFrame) {
        contoursChannel.offer(Tuple3(srcTipFrame, srcFrame, dstFrame))
    }

    private suspend fun processFrame(frames: Tuple3<TrackedFrame, TrackedFrame, TrackedFrame>) {
        val distance = { a: TrackedFrame, b: TrackedFrame ->
            a.contours.map { contour ->
                b.contours.map { contour.minDistance(it) }.min()!!
            }.min()!!.absoluteValue
        }

        val (tip, src, dst) = frames
        val tip2Dst = distance(tip, dst)
        val src2Dst = distance(src, dst)

        println("src2dst => $src2Dst")
        println("tip2dst => $tip2Dst")

        if (src2Dst < 65 || tip2Dst < 40) throw NullPointerException()

        if ((tip2Dst - 10) > src2Dst) arduinoControlRepository.turnRight()
        else arduinoControlRepository.goForward()
    }
}