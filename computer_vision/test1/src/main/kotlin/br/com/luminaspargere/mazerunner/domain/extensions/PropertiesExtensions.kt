package br.com.luminaspargere.mazerunner.domain.extensions

import arrow.core.Try
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

suspend fun Properties.load() {
    onBackground { Try { load(FileInputStream("config.ini")) } }
}

suspend fun Properties.save() {
    onBackground { Try { store(FileOutputStream("config.ini"), "") } }
}