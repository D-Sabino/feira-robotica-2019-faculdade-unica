package br.com.luminaspargere.mazerunner.presentation.main

import br.com.luminaspargere.mazerunner.App
import br.com.luminaspargere.mazerunner.domain.Injector
import br.com.luminaspargere.mazerunner.domain.videomanipulation.VideoCapture
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.videofeed.VideoFeedView
import org.koin.core.inject
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    private val videoCapture: VideoCapture by Injector.inject()

    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)
        prefWidth = 800.0
        prefHeight = 600.0
        padding = insets(16)
        center<VideoFeedView>()

        App.startStream(this@MainView, videoCapture.cameraImageFeed())
    }
}