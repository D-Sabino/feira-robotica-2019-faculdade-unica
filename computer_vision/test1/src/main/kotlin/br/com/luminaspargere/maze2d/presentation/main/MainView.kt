package br.com.luminaspargere.maze2d.presentation.main

import br.com.luminaspargere.maze2d.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.maze2d.presentation.remotecontroller.RemoteControllerView
import br.com.luminaspargere.maze2d.presentation.videofeed.VideoFeedView
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)
        prefWidth = 1024.0
        prefHeight = 768.0
        padding = insets(16)
        center<VideoFeedView>()
        bottom<RemoteControllerView>()
    }
}