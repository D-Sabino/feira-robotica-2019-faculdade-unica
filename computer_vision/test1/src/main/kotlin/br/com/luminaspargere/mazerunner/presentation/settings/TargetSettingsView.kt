package br.com.luminaspargere.mazerunner.presentation.settings

import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.extensions.labeledSlider
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import tornadofx.*

class TargetSettingsView : BaseScopedView() {
    override val root = vbox {
        padding = insets(5)
        style { borderColor += box(c("#808080")) }

        labeledSlider(Config.dstHueStart, 180) { v ->
            "Target hue start: $v"
        }
        labeledSlider(Config.dstSatStart, 255) { v ->
            "Target saturation start: $v"
        }
        labeledSlider(Config.dstValueStart, 255) { v ->
            "Target value start: $v"
        }

        separator {
            padding = insets(2)
        }

        labeledSlider(Config.dstHueEnd, 180) { v ->
            "Target hue end: $v"
        }
        labeledSlider(Config.dstSatEnd, 255) { v ->
            "Target saturation end: $v"
        }
        labeledSlider(Config.dstValueEnd, 255) { v ->
            "Target value end: $v"
        }
    }
}
