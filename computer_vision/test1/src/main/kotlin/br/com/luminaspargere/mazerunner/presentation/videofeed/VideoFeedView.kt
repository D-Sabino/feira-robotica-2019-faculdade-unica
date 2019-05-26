package br.com.luminaspargere.mazerunner.presentation.videofeed

import br.com.luminaspargere.mazerunner.domain.VideoCapture
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
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