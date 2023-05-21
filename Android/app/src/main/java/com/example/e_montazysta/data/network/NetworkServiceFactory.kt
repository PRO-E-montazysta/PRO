package com.example.e_montazysta.data.network

import com.example.e_montazysta.data.environments.Environment
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter

class NetworkServiceFactory(
    converterFactory: Converter.Factory,
    logLevel: HttpLoggingInterceptor.Level,
    environment: Environment
) : ServiceFactory(converterFactory, logLevel, environment) {

    override fun interceptors(): List<Interceptor> {
        return arrayListOf(NetworkRequestInterceptor(listOf()))
    }
}
