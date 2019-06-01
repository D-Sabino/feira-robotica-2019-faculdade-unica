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
    private val defaultWidth = 700.0
    private val defaultHeight = 600.0
    private val settingsPaneWidth = 180.0

    override val root = borderpane {
        importStylesheet(MainStyleSheet::class)
        prefWidth = defaultWidth
        prefHeight = defaultHeight

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
                    if (showing) r.scaleX(settingsPaneWidth.toInt(), 0)
                    else r.scaleX(0, settingsPaneWidth.toInt())
                    showing = !showing
                }
            }
        }

        r.widthProperty().addListener { _, _, v ->
            primaryStage.width = v.toDouble() + defaultWidth
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
}