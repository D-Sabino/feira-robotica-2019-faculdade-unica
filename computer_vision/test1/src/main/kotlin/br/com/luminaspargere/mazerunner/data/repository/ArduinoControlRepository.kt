package br.com.luminaspargere.mazerunner.data.repository

import arrow.effects.IO
import br.com.luminaspargere.mazerunner.data.api.ApiService
import br.com.luminaspargere.mazerunner.domain.Injector
import com.github.icarohs7.unoxcore.sideEffectBg
import org.koin.core.get

class ArduinoControlRepository {
    private val service: ApiService get() = Injector.get()

    suspend fun goForward(): IO<Unit> {
        return sideEffectBg { service.forward() }
    }

    suspend fun turnRight(): IO<Unit> {
        return sideEffectBg { service.right() }
    }

    suspend fun goBackward(): IO<Unit> {
        return sideEffectBg { service.backward() }
    }

    suspend fun turnLeft(): IO<Unit> {
        return sideEffectBg { service.left() }
    }

    suspend fun stop(): IO<Unit> {
        return sideEffectBg { service.stop() }
    }
}