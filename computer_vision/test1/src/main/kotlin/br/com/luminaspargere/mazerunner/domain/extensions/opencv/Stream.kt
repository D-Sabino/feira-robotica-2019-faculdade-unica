package br.com.luminaspargere.mazerunner.domain.extensions.opencv

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.opencv.core.Mat

fun Flow<Mat>.resized(width: Number, height: Number): Flow<Mat> {
    return this.map { it.resized(width, height) }
}