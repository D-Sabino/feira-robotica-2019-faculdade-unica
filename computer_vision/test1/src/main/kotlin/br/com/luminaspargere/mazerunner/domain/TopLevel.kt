package br.com.luminaspargere.mazerunner.domain

import javafx.util.StringConverter
import org.koin.core.KoinComponent

object Injector : KoinComponent

inline fun loop(block: () -> Unit): Nothing {
    while (true) block()
}

fun log(message: String) {
    println("Log/I -> $message")
}


@Suppress("FunctionName")
fun <T> StringConverter(transform: (T?) -> String): StringConverter<T> {
    return object : StringConverter<T>() {
        override fun toString(value: T?): String {
            return transform(value)
        }

        override fun fromString(value: String?): T {
            throw NotImplementedError()
        }
    }
}