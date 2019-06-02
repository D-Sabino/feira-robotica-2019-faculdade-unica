package br.com.luminaspargere.mazerunner

import br.com.luminaspargere.mazerunner.data.api.ApiService
import br.com.luminaspargere.mazerunner.data.repository.ArduinoControlRepository
import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.extensions.createRetrofitService
import br.com.luminaspargere.mazerunner.domain.extensions.load
import br.com.luminaspargere.mazerunner.presentation.main.MainView
import cz.adamh.utils.NativeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.opencv.core.Core
import tornadofx.App
import java.util.Properties

class App : App(MainView::class), CoroutineScope by MainScope() {
    init {
        NativeUtils.loadLibraryFromJar("/binaries/${Core.NATIVE_LIBRARY_NAME}.dll")

        val props = Properties()
        GlobalScope.launch {
            startKoin {
                modules(module {
                    single { ArduinoControlRepository() }
                    single { props }
                    single { createRetrofitService<ApiService>("${props.getOrDefault("address", "")}") }
                })
            }

            props.load()
            Config.init()
        }
    }
}