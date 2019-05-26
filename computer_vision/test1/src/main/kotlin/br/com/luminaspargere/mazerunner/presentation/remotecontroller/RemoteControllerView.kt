package br.com.luminaspargere.mazerunner.presentation.remotecontroller

import arrow.core.Try
import br.com.luminaspargere.mazerunner.data.api.ApiService
import br.com.luminaspargere.mazerunner.data.repository.ArduinoControlRepository
import br.com.luminaspargere.mazerunner.domain.Injector
import br.com.luminaspargere.mazerunner.domain.extensions.add
import br.com.luminaspargere.mazerunner.domain.extensions.createRetrofitService
import br.com.luminaspargere.mazerunner.presentation._baseclasses.BaseScopedView
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.geometry.Pos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.dsl.module
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign.MaterialDesign
import tornadofx.*
import java.io.FileOutputStream
import java.util.Properties

class RemoteControllerView : BaseScopedView() {
    private val arduinoControlRepository: ArduinoControlRepository by Injector.inject()
    private val props: Properties by Injector.inject()

    override val root = vbox {
        alignment = Pos.CENTER
        isFillWidth = false
        spacing = 6.0

        label("Endereço do arduino")

        hbox {
            add(JFXTextField()) {
                spacing = 4.0
                launch { text = onBackground { "${props.getOrDefault("address", text)}" } }
                focusedProperty().addListener { _, _, hasFocus ->
                    if (!hasFocus) Try {
                        launch(Dispatchers.Default) {
                            replaceApiService("http://$text")
                            props["address"] = text
                            props.store(FileOutputStream("config.ini"), "")
                        }
                    }
                }
            }
        }

        vbox {
            alignment = Pos.CENTER
            isFillWidth = false
            spacing = 4.0

            add(JFXButton()) {
                graphic = FontIcon(MaterialDesign.MDI_ARROW_UP)
                action { launch { arduinoControlRepository.goForward() } }
            }

            hbox {
                spacing = 4.0

                add(JFXButton()) {
                    graphic = FontIcon(MaterialDesign.MDI_ARROW_LEFT)
                    action { launch { arduinoControlRepository.turnLeft() } }
                }

                add(JFXButton()) {
                    graphic = FontIcon(MaterialDesign.MDI_CLOSE)
                    action { launch { arduinoControlRepository.stop() } }
                    requestFocus()
                }

                add(JFXButton()) {
                    graphic = FontIcon(MaterialDesign.MDI_ARROW_RIGHT)
                    action { launch { arduinoControlRepository.turnRight() } }
                }
            }

            add(JFXButton()) {
                graphic = FontIcon(MaterialDesign.MDI_ARROW_DOWN)
                action { launch { arduinoControlRepository.goBackward() } }
            }
        }
    }

    private fun replaceApiService(address: String) {
        loadKoinModules(module {
            single(override = true) { createRetrofitService<ApiService>(address) }
        })
    }
}