package br.com.luminaspargere.mazerunner.presentation.settings

import br.com.luminaspargere.mazerunner.domain.Config
import br.com.luminaspargere.mazerunner.domain.StringConverter
import br.com.luminaspargere.mazerunner.domain.extensions.add
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import com.jfoenix.controls.JFXSlider
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.geometry.Pos
import tornadofx.*

class SettingsView : BaseScopedView() {
    override val root = vbox {
        padding = insets(10)
        alignment = Pos.CENTER

        vbox {
            padding = insets(5)
            style {
                borderColor += box(c("#808080"))
            }

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
                padding = insets(10)
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

        separator {
            padding = insets(20)
        }

        vbox {
            padding = insets(5)
            style {
                borderColor += box(c("#808080"))
            }

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
                padding = insets(5)
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

    private fun EventTarget.labeledSlider(
            property: ObservableValue<Number>,
            upperBound: Number,
            converter: (Number?) -> String
    ) {
        label(property, converter = StringConverter { converter(it) })
        add(JFXSlider(0.0, upperBound.toDouble(), property.value?.toDouble() ?: 0.0)) {
            bind(property)
            blockIncrement = 1.0
        }
    }
}
