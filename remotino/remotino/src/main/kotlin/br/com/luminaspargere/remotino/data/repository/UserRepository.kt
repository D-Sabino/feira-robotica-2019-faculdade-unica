package br.com.luminaspargere.remotino.data.repository

import arrow.effects.IO
import base.corelibrary.data.entities.User
import base.corelibrary.presentation.CoreNavigation
import com.github.icarohs7.unoxcore.sideEffectBg

class UserRepository {
    suspend fun logout(): IO<Unit> {
        return sideEffectBg {
            User.clear()
            CoreNavigation.loginActivity()
        }
    }
}
