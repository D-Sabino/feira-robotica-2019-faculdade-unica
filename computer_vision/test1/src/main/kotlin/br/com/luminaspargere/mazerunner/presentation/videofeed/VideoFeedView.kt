package br.com.luminaspargere.mazerunner.presentation.videofeed

import br.com.luminaspargere.mazerunner.domain.extensions.showOpenCvStream
import br.com.luminaspargere.mazerunner.domain.videomanipulation.CameraStream
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import kotlinx.coroutines.launch
import tornadofx.*

class VideoFeedView : BaseScopedView() {
    override val root = stackpane {
        imageview {
            launch { showOpenCvStream(CameraStream.finalCameraOutput) }
        }
    }
}