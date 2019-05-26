package br.com.luminaspargere.maze2d

import br.com.luminaspargere.maze2d.presentation.MainView
import cz.adamh.utils.NativeUtils
import org.opencv.core.Core
import tornadofx.App

class App : App(MainView::class) {
    init {
        NativeUtils.loadLibraryFromJar("/binaries/${Core.NATIVE_LIBRARY_NAME}.dll")
    }
}