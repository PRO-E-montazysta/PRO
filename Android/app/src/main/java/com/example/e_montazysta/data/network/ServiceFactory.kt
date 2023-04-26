package com.example.e_montazysta.data.network

import com.example.e_montazysta.data.environments.Environment
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

abstract class ServiceFactory(
    private val converterFactory: Converter.Factory,
    private val logLevel: HttpLoggingInterceptor.Level,
    private val environment: Environment
) {

    fun <T> create(serviceType: Class<T>): T {
        return create(
            serviceType,
            getHttpClient(),
            converterFactory,
            environment.baseUrl
        )
    }

    private fun getNetAdapter(
        client: OkHttpClient,
        converterFactory: Converter.Factory,
        baseUrl: String
    ): Retrofit {
        val builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
        return builder.build()
    }

    private fun <T> create(
        serviceType: Class<T>,
        client: OkHttpClient,
        converterFactory: Converter.Factory,
        baseUrl: String
    ): T {
        val retrofit = getNetAdapter(client, converterFactory, baseUrl)
        return retrofit.create(serviceType)
    }

    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        interceptors().forEach { interceptor -> builder.addInterceptor(interceptor) }
        builder.addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
        return builder.build()
    }

    abstract fun interceptors(): List<Interceptor>
}