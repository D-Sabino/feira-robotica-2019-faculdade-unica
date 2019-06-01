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

            labeledSlider(Config.srcHueStart) { v ->
                "Robot hue start: $v"
            }
            labeledSlider(Config.srcSatStart) { v ->
                "Robot saturation start: $v"
            }
            labeledSlider(Config.srcValueStart) { v ->
                "Robot value start: $v"
            }

            separator {
                padding = insets(10)
            }

            labeledSlider(Config.srcHueEnd) { v ->
                "Robot hue end: $v"
            }
            labeledSlider(Config.srcSatEnd) { v ->
                "Robot saturation end: $v"
            }
            labeledSlider(Config.srcValueEnd) { v ->
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

            labeledSlider(Config.dstHueStart) { v ->
                "Target hue start: $v"
            }
            labeledSlider(Config.dstSatStart) { v ->
                "Target saturation start: $v"
            }
            labeledSlider(Config.dstValueStart) { v ->
                "Target value start: $v"
            }

            separator {
                padding = insets(5)
            }

            labeledSlider(Config.dstHueEnd) { v ->
                "Target hue end: $v"
            }
            labeledSlider(Config.dstSatEnd) { v ->
                "Target saturation end: $v"
            }
            labeledSlider(Config.dstValueEnd) { v ->
                "Target value end: $v"
            }
        }
    }

    private fun EventTarget.labeledSlider(property: ObservableValue<Number>, converter: (Number?) -> String) {
        label(property, converter = StringConverter { converter(it) })
        add(JFXSlider(0.0, 255.0, property.value?.toDouble() ?: 0.0)) {
            bind(property)
        }
    }
}
