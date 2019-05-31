package br.com.luminaspargere.mazerunner.presentation.textoutput

import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import br.com.luminaspargere.mazerunner.presentation._communication.Messages
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tornadofx.*

class TextOutputView : BaseScopedView() {
    override val root = stackpane {
        text {
            launch { Messages.infoStream.collect { text = it } }
        }
    }
}
