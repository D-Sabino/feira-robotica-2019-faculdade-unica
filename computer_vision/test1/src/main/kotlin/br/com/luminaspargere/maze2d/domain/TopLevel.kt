package br.com.luminaspargere.maze2d.domain

import org.koin.core.KoinComponent

object Injector : KoinComponent

inline fun loop(block: () -> Unit): Nothing {
    while (true) block()
}

fun log(message: String) {
    println("Log/I -> $message")
}