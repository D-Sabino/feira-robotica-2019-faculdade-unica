package br.com.luminaspargere.mazerunner.presentation.videofeed

import br.com.luminaspargere.mazerunner.domain.extensions.opencv.resized
import br.com.luminaspargere.mazerunner.domain.extensions.opencv.tracking.activateObjectsTrackingAndControlRobot
import br.com.luminaspargere.mazerunner.domain.extensions.showOpenCvStream
import br.com.luminaspargere.mazerunner.domain.video.CameraStream
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation.main.MainView
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.launch
import tornadofx.*

class VideoFeedView : BaseScopedView() {
    override val root = hbox {
        alignment = Pos.CENTER
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        imageview {
            val stream = CameraStream.channel
                    .map { it.resized(MainView.IMAGES_WIDTH, MainView.IMAGES_HEIGHT) }
                    .map { it.activateObjectsTrackingAndControlRobot() }
            launch { showOpenCvStream(stream) }
        }
    }
}