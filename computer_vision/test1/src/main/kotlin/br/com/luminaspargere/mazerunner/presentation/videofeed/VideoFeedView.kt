package br.com.luminaspargere.mazerunner.presentation.videofeed

import br.com.luminaspargere.mazerunner.domain.extensions.showOpenCvStream
import br.com.luminaspargere.mazerunner.domain.video.ProcessedOutputs
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import kotlinx.coroutines.launch
import tornadofx.*

class VideoFeedView : BaseScopedView() {
    override val root = hbox {
        alignment = Pos.CENTER
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        vbox {
            alignment = Pos.CENTER

            imageview {
                launch { showOpenCvStream(ProcessedOutputs.robotTrackingOutput()) }
            }

            imageview {
                launch { showOpenCvStream(ProcessedOutputs.targetTrackingOutput()) }
            }
        }

        imageview {
            launch { showOpenCvStream(ProcessedOutputs.originalOutput()) }
        }
    }
}