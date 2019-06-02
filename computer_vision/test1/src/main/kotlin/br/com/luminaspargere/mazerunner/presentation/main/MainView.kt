package br.com.luminaspargere.mazerunner.presentation.main

import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.settings.SettingsView
import br.com.luminaspargere.mazerunner.presentation.videobuttonpanel.VideoButtonPanelView
import br.com.luminaspargere.mazerunner.presentation.videofeed.VideoFeedView
import javafx.geometry.Pos
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    init {
        primaryStage.isResizable = false
    }

    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)
        prefWidth = DEFAULT_WIDTH
        prefHeight = DEFAULT_HEIGHT

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

        r.widthProperty().addListener { _, _, v ->
            primaryStage.width = v.toDouble() + DEFAULT_WIDTH
        }

        top = stackpane {}
        right = r
        center = c
        left = stackpane {}
        bottom = stackpane {}
    }


    companion object {
        const val DEFAULT_WIDTH: Double = 1280.0
        const val DEFAULT_HEIGHT: Double = 720.0

        const val IMAGES_WIDTH: Double = 1000.0
        const val IMAGES_HEIGHT: Double = 500.0

        const val SETTINGS_PANE_WIDTH = 180.0
    }
}