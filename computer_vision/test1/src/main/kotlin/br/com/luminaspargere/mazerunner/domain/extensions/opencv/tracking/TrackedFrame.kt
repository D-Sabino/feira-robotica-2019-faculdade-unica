package br.com.luminaspargere.mazerunner.domain.extensions.opencv.tracking

import br.com.luminaspargere.mazerunner.domain.extensions.opencv.Contour
import org.opencv.core.Mat

data class TrackedFrame(
        val filteredFrame: Mat,
        val destinationFrame: Mat,
        val contours: List<Contour>
)