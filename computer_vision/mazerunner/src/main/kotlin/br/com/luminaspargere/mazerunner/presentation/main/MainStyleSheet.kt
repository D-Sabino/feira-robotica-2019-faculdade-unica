package br.com.luminaspargere.mazerunner.presentation.main

import tornadofx.*

class MainStyleSheet : Stylesheet() {
    init {
        importStylesheet("/css/ikonli.css")
        importStylesheet("/css/jfoenix.css")

        jfxButton {
            textFill = c("#ffffff")
            backgroundColor += c("#5264AE")
        }
    }

    companion object {
        val jfxButton by cssclass()
    }
}