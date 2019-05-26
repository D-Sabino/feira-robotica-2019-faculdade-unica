package br.com.luminaspargere.maze2d.presentation.home

import br.com.luminaspargere.maze2d.domain.VideoCapture
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import javafx.embed.swing.SwingFXUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import tornadofx.*

class VideoFeedView : View("Visão Computacional"), CoroutineScope by MainScope() {
    override val root = vbox {
        imageview {
            launch {
                VideoCapture.feed()
                        .flowOn(Dispatchers.Default)
                        .collect { image = SwingFXUtils.toFXImage(it, null) }
            }
        }
    }

    override fun onDelete() {
        cancelCoroutineScope()
        super.onDelete()
    }
}