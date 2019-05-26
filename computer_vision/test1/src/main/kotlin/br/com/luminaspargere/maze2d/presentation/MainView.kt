package br.com.luminaspargere.maze2d.presentation

import br.com.luminaspargere.maze2d.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.maze2d.presentation.videofeed.VideoFeedView
import tornadofx.*

class MainView : BaseScopedView("Visão Computacional") {
    override val root = borderpane {
        center {
            add(VideoFeedView())
        }

        bottom {

        }
    }
}