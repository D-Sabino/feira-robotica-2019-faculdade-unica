package br.com.luminaspargere.mazerunner.presentation.main

import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.settings.SettingsView
import br.com.luminaspargere.mazerunner.presentation.videofeed.VideoFeedView
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)
        prefWidth = 1024.0
        prefHeight = 600.0
        center<VideoFeedView>()
        right<SettingsView>()
    }
}