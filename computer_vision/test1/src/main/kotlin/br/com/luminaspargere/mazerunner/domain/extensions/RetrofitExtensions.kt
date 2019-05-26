package br.com.luminaspargere.mazerunner.domain.extensions

import br.com.luminaspargere.mazerunner.domain.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

/**
 * Short hand version to create a retrofit instance
 * with the given [baseUrl] and with that create
 * an instance of a given service, which also
 * modifies the response, adding or setting the
 * "Content-Type" header to "application/json"
 */
inline fun <reified T> createRetrofitServiceJsonResponse(
        baseUrl: String,
        noinline clientExtraConfig: OkHttpClient.Builder.() -> Unit = {},
        builderExtraConfig: Retrofit.Builder.() -> Unit = {}
): T {
    val clientSetup: OkHttpClient.Builder.() -> Unit = {
        addInterceptor {
            val req = it.request()
            val res = it.proceed(req)
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
            res
        }
    }

    return createRetrofitService(baseUrl, {
        clientSetup()
        clientExtraConfig()
    }, builderExtraConfig)
}

/**
 * Short hand version to create a retrofit instance
 * with the given [baseUrl] and with that create
 * an instance of a given service
 */
inline fun <reified T> createRetrofitService(
        baseUrl: String,
        noinline clientExtraConfig: OkHttpClient.Builder.() -> Unit = {},
        builderExtraConfig: Retrofit.Builder.() -> Unit = {}
): T {
    return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(RetrofitExtensions.getHttpClient(clientExtraConfig))
            .apply(builderExtraConfig)
            .build()
            .create()
}

object RetrofitExtensions {
    fun getHttpClient(clientExtraConfig: OkHttpClient.Builder.() -> Unit = {}): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(::log)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient
                .Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .apply(clientExtraConfig)
                .build()
    }
}