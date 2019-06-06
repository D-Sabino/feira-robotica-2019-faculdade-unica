package br.com.luminaspargere.mazerunner.presentation.main

import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.settings.SettingsView
import br.com.luminaspargere.mazerunner.presentation.textoutput.TextOutputView
import br.com.luminaspargere.mazerunner.presentation.videobuttonpanel.VideoButtonPanelView
import br.com.luminaspargere.mazerunner.presentation.videofeed.VideoFeedView
import javafx.geometry.Pos
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    init {
        primaryStage.isMaximized = true
    }

    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)

        val r = stackpane {
            minWidth = 0.0
            prefWidth = 0.0
            opacity = 0.0
            add(SettingsView())
        }

        val c = hbox {
            alignment = Pos.CENTER
            add(VideoFeedView())
            add(VideoButtonPanelView(r))
        }

        val b = stackpane {
            add(TextOutputView())
        }

        top = stackpane {}
        right = r
        center = c
        left = stackpane {}
        bottom = b
    }


    companion object {
        const val IMAGES_WIDTH: Double = 1100.0
        const val IMAGES_HEIGHT: Double = 600.0

        const val SETTINGS_PANE_WIDTH = 180.0
    }
}