package br.com.luminaspargere.mazerunner.domain.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.flowOnBg(): Flow<T> {
    return this.flowOn(Dispatchers.Default)
}