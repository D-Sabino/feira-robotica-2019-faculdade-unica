package br.com.luminaspargere.mazerunner.presentation.videobuttonpanel

import br.com.luminaspargere.mazerunner.domain.extensions.add
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.tracking.ObjectTracking
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.main.MainView
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXToggleButton
import javafx.geometry.Pos
import javafx.scene.layout.Pane
import javafx.util.Duration
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.material.Material
import tornadofx.*

class VideoButtonPanelView(settingsPane: Pane) : BaseScopedView() {
    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 5.0

        add(JFXToggleButton()) {
            this.selectedProperty().addListener { _, _, isActive ->
                ObjectTracking.isActive = isActive
            }
        }

        add(JFXButton()) {
            graphic = FontIcon(Material.CHEVRON_RIGHT)
            prefHeight = 140.0
            var showing = false
            action {
                if (showing) settingsPane.scaleX(MainView.SETTINGS_PANE_WIDTH.toInt(), 0)
                else settingsPane.scaleX(0, MainView.SETTINGS_PANE_WIDTH.toInt())
                showing = !showing
            }
        }
    }

    private fun Pane.scaleX(from: Int, to: Int) {
        timeline {
            keyframe(Duration.millis(500.0)) {
                keyvalue(prefWidthProperty(), from)
                keyvalue(prefWidthProperty(), to)
                if (to == 0) keyvalue(opacityProperty(), 0)
                else keyvalue(opacityProperty(), 1)
            }
        }
    }
}
