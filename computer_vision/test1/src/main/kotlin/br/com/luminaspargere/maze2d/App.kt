package br.com.luminaspargere.maze2d

import arrow.core.Try
import br.com.luminaspargere.maze2d.data.api.ApiService
import br.com.luminaspargere.maze2d.data.repository.ArduinoControlRepository
import br.com.luminaspargere.maze2d.domain.extensions.createRetrofitService
import br.com.luminaspargere.maze2d.presentation.main.MainView
import cz.adamh.utils.NativeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.opencv.core.Core
import tornadofx.App
import java.io.FileInputStream
import java.util.Properties

class App : App(MainView::class), CoroutineScope by MainScope() {
    init {
        NativeUtils.loadLibraryFromJar("/binaries/${Core.NATIVE_LIBRARY_NAME}.dll")

        val props = Properties()
        GlobalScope.launch {
            Try { props.load(FileInputStream("config.ini")) }
            startKoin {
                modules(module {
                    single { ArduinoControlRepository() }
                    single { props }
                    single { createRetrofitService<ApiService>("${props.getOrDefault("address", "")}") }
                })
            }
        }
    }
}