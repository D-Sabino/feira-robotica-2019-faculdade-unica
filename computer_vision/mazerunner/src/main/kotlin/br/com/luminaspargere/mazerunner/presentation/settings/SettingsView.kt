package br.com.luminaspargere.mazerunner.presentation.settings

import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import javafx.geometry.Pos
import tornadofx.*

class SettingsView : BaseScopedView() {
    override val root = vbox {
        padding = insets(5)
        alignment = Pos.CENTER

        add(RobotTipSettingsView())
        separator { padding = insets(10) }
        add(RobotSettingsView())
        separator { padding = insets(10) }
        add(TargetSettingsView())
    }
}
