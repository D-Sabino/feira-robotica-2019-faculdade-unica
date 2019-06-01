package br.com.luminaspargere.mazerunner.presentation.main

import br.com.luminaspargere.mazerunner.domain.extensions.add
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.settings.SettingsView
import br.com.luminaspargere.mazerunner.presentation.videofeed.VideoFeedView
import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.layout.Pane
import javafx.util.Duration
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.material.Material
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
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
            add(JFXButton()) {
                graphic = FontIcon(Material.CHEVRON_RIGHT)
                prefHeight = 140.0
                var showing = false
                action {
                    if (showing) r.scaleX(SETTINGS_PANE_WIDTH.toInt(), 0)
                    else r.scaleX(0, SETTINGS_PANE_WIDTH.toInt())
                    showing = !showing
                }
            }
        }

        r.widthProperty().addListener { _, _, v ->
            primaryStage.width = v.toDouble() + DEFAULT_WIDTH
            println(v)
        }

        top = stackpane {}
        right = r
        center = c
        left = stackpane {}
        bottom = stackpane {}
    }

    private fun Pane.scaleX(from: Int, to: Int) {
        timeline {
            keyframe(Duration.seconds(1.0)) {
                keyvalue(prefWidthProperty(), from)
                keyvalue(prefWidthProperty(), to)
                if (to == 0) keyvalue(opacityProperty(), 0)
                else keyvalue(opacityProperty(), 1)
            }
        }
    }

    companion object {
        const val DEFAULT_WIDTH: Double = 1280.0
        const val DEFAULT_HEIGHT: Double = 720.0

        const val IMAGES_WIDTH: Double = 1000.0
        const val IMAGES_HEIGHT: Double = 500.0

        const val SETTINGS_PANE_WIDTH = 180.0
    }
}