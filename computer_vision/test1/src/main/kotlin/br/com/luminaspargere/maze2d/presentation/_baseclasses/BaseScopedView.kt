package br.com.luminaspargere.maze2d.presentation._baseclasses

import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import javafx.scene.Node
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import tornadofx.*

abstract class BaseScopedView(
        title: String? = null,
        icon: Node? = null
) : View(title, icon), CoroutineScope by MainScope() {
    override fun onDelete() {
        cancelCoroutineScope()
        super.onDelete()
    }
}