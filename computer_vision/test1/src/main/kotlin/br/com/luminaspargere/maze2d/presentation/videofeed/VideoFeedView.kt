package br.com.luminaspargere.maze2d.presentation.videofeed

import br.com.luminaspargere.maze2d.domain.VideoCapture
import br.com.luminaspargere.maze2d.presentation._baseclasses.BaseScopedView
import javafx.embed.swing.SwingFXUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import tornadofx.*

class VideoFeedView : BaseScopedView() {
    override val root = stackpane {
        imageview {
            launch {
                VideoCapture.feed()
                        .flowOn(Dispatchers.Default)
                        .collect { image = SwingFXUtils.toFXImage(it.image, null) }
            }
        }
    }
}