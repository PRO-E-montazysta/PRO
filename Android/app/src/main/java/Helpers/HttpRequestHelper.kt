package Helpers

import Enums.RequestType
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class HttpRequestHelper {
    companion object {
        inline fun <T1, reified T2> sendHttpRequest(type: RequestType, url: String, data: T1, token: String? = null): T2 {
            return when (type) {
                RequestType.GET -> sendHttpGetRequest(url, data, T2::class.java, token)
                RequestType.POST -> sendHttpPostRequest(url, data, T2::class.java, token)
                else -> {
                    throw NotImplementedError("Nieobsługiwany typ zapytania http")
                }
            }
        }

        @PublishedApi
        internal fun <T1, T2> sendHttpPostRequest(url: String, data: T1, ofClass: Class<T2>, token: String? = null): T2 {
            return runBlocking {
                var headers = emptyMap<String, String>();
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token");
                }
                val (request, response, result) = Fuel.post(url)
                    .header(headers)
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
        internal fun <T1, T2> sendHttpGetRequest(url: String, data: T1, ofClass: Class<T2>, token: String? = null): T2 {
            return runBlocking {
                var headers = emptyMap<String, String>();
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token");
                }
                val (request, response, result) = Fuel.get(url)
                    .header(headers)
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