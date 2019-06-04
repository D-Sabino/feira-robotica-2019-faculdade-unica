package br.com.luminaspargere.mazerunner.presentation.settings

import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.extensions.labeledSlider
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import tornadofx.*

class RobotTipSettingsView : BaseScopedView() {
    override val root = vbox {
        padding = insets(5)
        style { borderColor += box(c("#808080")) }

        labeledSlider(Config.srcTipHueStart, 180) { v ->
            "Robot tip hue start: $v"
        }
        labeledSlider(Config.srcTipSatStart, 255) { v ->
            "Robot tip saturation start: $v"
        }
        labeledSlider(Config.srcTipValueStart, 255) { v ->
            "Robot tip value start: $v"
        }

        separator {
            padding = insets(2)
        }

        labeledSlider(Config.srcTipHueEnd, 180) { v ->
            "Robot tip hue end: $v"
        }
        labeledSlider(Config.srcTipSatEnd, 255) { v ->
            "Robot tip saturation end: $v"
        }
        labeledSlider(Config.srcTipValueEnd, 255) { v ->
            "Robot tip value end: $v"
        }
    }
}
