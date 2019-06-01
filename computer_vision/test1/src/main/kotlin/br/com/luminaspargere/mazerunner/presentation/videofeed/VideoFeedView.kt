package br.com.luminaspargere.mazerunner.presentation.videofeed

import br.com.luminaspargere.mazerunner.domain.extensions.opencv.resized
import br.com.luminaspargere.mazerunner.domain.extensions.showOpenCvStream
import br.com.luminaspargere.mazerunner.domain.videomanipulation.CameraStream
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.main.MainView
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
            val w = MainView.IMAGES_WIDTH / 2
            val h = MainView.IMAGES_HEIGHT / 2

            imageview {
                launch { showOpenCvStream(CameraStream.robotTrackingOutput.resized(w, h)) }
            }

            imageview {
                launch { showOpenCvStream(CameraStream.targetTrackingOutput.resized(w, h)) }
            }
        }

        imageview {
            val w = MainView.IMAGES_WIDTH / 2
            val h = MainView.IMAGES_HEIGHT

            launch { showOpenCvStream(CameraStream.finalCameraOutput.resized(w, h)) }
        }
    }
}