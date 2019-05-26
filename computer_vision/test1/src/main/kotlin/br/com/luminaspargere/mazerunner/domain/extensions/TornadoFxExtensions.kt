package br.com.luminaspargere.mazerunner.domain.extensions

import javafx.event.EventTarget
import javafx.scene.Node
import tornadofx.*

fun <T : Node> EventTarget.add(node: T, op: T.() -> Unit = {}): T {
    return node.attachTo(this, op)
}