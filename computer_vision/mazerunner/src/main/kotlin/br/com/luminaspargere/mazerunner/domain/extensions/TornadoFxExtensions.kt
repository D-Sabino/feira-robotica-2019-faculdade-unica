package br.com.luminaspargere.mazerunner.domain.extensions

import br.com.luminaspargere.mazerunner.domain.StringConverter
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.toImage
import com.github.icarohs7.unoxcore.extensions.coroutines.forEach
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.github.icarohs7.unoxcore.extensions.coroutines.onForeground
import com.jfoenix.controls.JFXSlider
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.image.ImageView
import kotlinx.coroutines.channels.ReceiveChannel
import org.opencv.core.Mat
import tornadofx.*

fun <T : Node> EventTarget.add(node: T, op: T.() -> Unit = {}): T {
    return node.attachTo(this, op)
}

suspend fun ImageView.showOpenCvStream(stream: ReceiveChannel<Mat>) {
    stream.forEach { onForeground { image = onBackground { it.toImage() } } }
}

fun EventTarget.labeledSlider(
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