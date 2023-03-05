package Helpers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class HttpRequestHelper {
    companion object {
        inline fun <T1, reified T2> sendHttpPostRequest(url: String, data: T1, token: String? = null): T2 {
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
                            return@runBlocking Gson().fromJson(data, T2::class.java) },
                        { error -> throw Throwable(error) }
                    )
            }
        }

        inline fun <T1, reified T2> sendHttpGetRequest(url: String, data: T1, token: String? = null): T2 {
            return runBlocking {
                var headers = emptyMap<String, String>();
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token");
                }
                val (request, response, result) = Fuel.get(url)
                    .header(headers)
                    .awaitStringResponseResult()
                result.fold(
                    { data ->
                        var i = 1
                        return@runBlocking Gson().fromJson(data, T2::class.java) },
                    { error -> throw Throwable(error) }
                )
            }
        }

        inline fun <reified T> sendHttpPutRequest(url: String, data: T, token: String? = null) : T {
            return runBlocking {
                var headers = emptyMap<String, String>();
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token");
                }
                val (request, response, result) = Fuel.put(url)
                    .header(headers)
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                    { data ->
                        var i = 1
                        return@runBlocking Gson().fromJson(data, T::class.java) },
                    { error -> throw Throwable(error) }
                )
            }
        }

        inline fun sendHttpDeleteRequest(url: String, token: String? = null) : Boolean {
            return runBlocking {
                var headers = emptyMap<String, String>();
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token");
                }
                val (request, response, result) = Fuel.delete(url)
                    .header(headers)
                    .awaitStringResponseResult()
                result.fold(
                    { data ->
                        var i = 1
                        return@runBlocking true },
                    { error -> throw Throwable(error) }
                )
            }
        }
    }
}