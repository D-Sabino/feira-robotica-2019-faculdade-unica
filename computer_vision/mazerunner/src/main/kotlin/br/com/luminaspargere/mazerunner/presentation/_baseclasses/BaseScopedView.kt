package br.com.luminaspargere.mazerunner.presentation._baseclasses

import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import javafx.scene.Node
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import tornadofx.*

abstract class BaseScopedView(
        title: String? = null,
        icon: Node? = null
) : View(title, icon), CoroutineScope by MainScope() {
    override fun onUndock() {
        cancelCoroutineScope()
        super.onDelete()
    }
}