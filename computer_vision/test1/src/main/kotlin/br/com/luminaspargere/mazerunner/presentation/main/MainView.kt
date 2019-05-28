package br.com.luminaspargere.mazerunner.presentation.main

import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.remotecontroller.RemoteControllerView
import br.com.luminaspargere.mazerunner.presentation.videofeed.VideoFeedView
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)
        prefWidth = 800.0
        prefHeight = 600.0
        padding = insets(16)
        center<VideoFeedView>()
        bottom<RemoteControllerView>()
    }
}