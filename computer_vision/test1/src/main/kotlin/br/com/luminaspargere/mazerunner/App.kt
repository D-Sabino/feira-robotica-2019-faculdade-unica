package br.com.luminaspargere.mazerunner

import arrow.core.Try
import br.com.luminaspargere.mazerunner.data.api.ApiService
import br.com.luminaspargere.mazerunner.data.repository.ArduinoControlRepository
import br.com.luminaspargere.mazerunner.domain.Injector
import br.com.luminaspargere.mazerunner.domain.extensions.createRetrofitService
import br.com.luminaspargere.mazerunner.domain.videomanipulation.VideoCapture
import br.com.luminaspargere.mazerunner.presentation.main.MainView
import cz.adamh.utils.NativeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import org.opencv.core.Core
import org.opencv.core.Mat
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
                    single { VideoCapture() }
                })
            }
        }
    }

    companion object {
        private val videoCapture by lazy { Injector.get<VideoCapture>() }
        val imageStream: Channel<Mat> = Channel(Channel.CONFLATED)

        suspend fun startStream(stream: Flow<Mat>) {
            videoCapture.open()
            stream.collect { imageStream.offer(it) }
        }
    }
}