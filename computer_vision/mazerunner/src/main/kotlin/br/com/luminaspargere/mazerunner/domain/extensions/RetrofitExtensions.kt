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
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .apply(clientExtraConfig)
                .build()
    }
}