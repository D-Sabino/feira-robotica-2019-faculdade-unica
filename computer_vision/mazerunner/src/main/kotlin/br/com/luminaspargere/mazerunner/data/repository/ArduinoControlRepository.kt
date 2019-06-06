package br.com.luminaspargere.mazerunner.data.repository

import arrow.core.Try
import br.com.luminaspargere.mazerunner.data.api.ApiService
import br.com.luminaspargere.mazerunner.domain.Injector
import com.github.icarohs7.unoxcore.tryBg
import org.koin.core.get

class ArduinoControlRepository {
    private val service: ApiService get() = Injector.get()

    suspend fun goForward(): Try<Unit> = tryBg {
        service.forward()
    }

    suspend fun turnRight(): Try<Unit> = tryBg {
        service.right()
    }

    suspend fun goBackward(): Try<Unit> = tryBg {
        service.backward()
    }

    suspend fun turnLeft(): Try<Unit> = tryBg {
        service.left()
    }

    suspend fun stop(): Try<Unit> {
        return tryBg { service.stop() }
    }
}