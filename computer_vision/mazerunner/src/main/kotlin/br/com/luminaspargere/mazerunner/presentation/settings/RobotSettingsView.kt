package br.com.luminaspargere.mazerunner.presentation.settings

import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.extensions.labeledSlider
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import tornadofx.*

class RobotSettingsView : BaseScopedView() {
    override val root = vbox {
        padding = insets(5)
        style { borderColor += box(c("#808080")) }

        labeledSlider(Config.srcHueStart, 180) { v ->
            "Robot hue start: $v"
        }
        labeledSlider(Config.srcSatStart, 255) { v ->
            "Robot saturation start: $v"
        }
        labeledSlider(Config.srcValueStart, 255) { v ->
            "Robot value start: $v"
        }

        separator {
            padding = insets(2)
        }

        labeledSlider(Config.srcHueEnd, 180) { v ->
            "Robot hue end: $v"
        }
        labeledSlider(Config.srcSatEnd, 255) { v ->
            "Robot saturation end: $v"
        }
        labeledSlider(Config.srcValueEnd, 255) { v ->
            "Robot value end: $v"
        }
    }
}
