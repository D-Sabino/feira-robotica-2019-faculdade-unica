package br.com.luminaspargere.mazerunner.presentation._communication

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.reactive.flow.asFlow

object Messages {
    private val infoChannel = Channel<String>()
    val infoStream: Flow<String> = infoChannel.asPublisher().asFlow()

    fun i(message: String) {
        infoChannel.offer(message)
    }
}