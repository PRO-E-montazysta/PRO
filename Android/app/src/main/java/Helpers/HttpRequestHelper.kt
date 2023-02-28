package Helpers

import Enums.RequestType
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HttpRequestHelper {
    companion object {
        inline fun <T1, reified T2> sendHttpRequest(type: RequestType, url: String, data: T1): T2 {
            return when (type) {
                RequestType.GET -> sendHttpGetRequest(url, data, T2::class.java)
                RequestType.POST -> sendHttpPostRequest(url, data, T2::class.java)
                else -> {
                    throw NotImplementedError("Nieobsługiwany typ zapytania http")
                }
            }
        }

        inline fun <T1, reified T2> sendHttpRequest(type: RequestType, url: String, token: String, data: T1): T2 {
            return when (type) {
                RequestType.GET -> sendHttpGetRequest(url, data, T2::class.java)
                RequestType.POST -> sendHttpPostRequest(url, data, T2::class.java)
                else -> {
                    throw NotImplementedError("Nieobsługiwany typ zapytania http")
                }
            }
        }

        @PublishedApi
        internal fun <T1, T2> sendHttpPostRequest(url: String, data: T1, ofClass: Class<T2>): T2 {
            return runBlocking {
                val (request, response, result) = Fuel.post(url)
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                        { data ->
                            var i = 1
                            return@runBlocking Gson().fromJson(data, ofClass) },
                        { error -> throw Throwable(error) }
                    )
            }
        }

        @PublishedApi
        internal fun <T1, T2> sendHttpGetRequest(url: String, data: T1, ofClass: Class<T2>): T2 {
            return runBlocking {
                val (request, response, result) = Fuel.get(url)
                    .header(mapOf("Authorization" to "Bearer AbCdEf123456"))
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                    { data ->
                        var i = 1
                        return@runBlocking Gson().fromJson(data, ofClass) },
                    { error -> throw Throwable(error) }
                )
            }
        }

        @PublishedApi
        internal fun <T1, T2> sendHttpPostRequest(url: String, data: T1, token: String, ofClass: Class<T2>): T2 {
            return runBlocking {
                val (request, response, result) = Fuel.post(url)
                    .header(mapOf("Authorization" to "Bearer $token"))
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                    { data ->
                        var i = 1
                        return@runBlocking Gson().fromJson(data, ofClass) },
                    { error -> throw Throwable(error) }
                )
            }
        }

        @PublishedApi
        internal fun <T1, T2> sendHttpGetRequest(url: String, data: T1, token: String, ofClass: Class<T2>): T2 {
            return runBlocking {
                val (request, response, result) = Fuel.get(url)
                    .header(mapOf("Authorization" to "Bearer $token"))
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                    { data ->
                        var i = 1
                        return@runBlocking Gson().fromJson(data, ofClass) },
                    { error -> throw Throwable(error) }
                )
            }
        }
    }
}