package br.com.luminaspargere.remotino.data.api

import retrofit2.http.GET

/**
 * Access to the server
 * APIs
 */
interface ApiService {
    @GET("?FRENTE")
    suspend fun forward()

    @GET("?DIREITA")
    suspend fun right()

    @GET("?TRAS")
    suspend fun backward()

    @GET("?ESQUERDA")
    suspend fun left()

    @GET("?PARAR")
    suspend fun stop()
}
