package com.example.e_montazysta.helpers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder

class HttpRequestHelper {
    companion object {
        val baseUrl = "https://dev.emontazysta.pl/api/v1"
        inline fun <T1, reified T2> sendHttpToolRequest(url: String = baseUrl, data: T1, token: String? = null): T2 {
            var response = runBlocking {
                var headers = emptyMap<String, String>()
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token")
                }
                val (request, response, result) = Fuel.post(url)
                    .header(headers)
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                        { data -> return@runBlocking data},
                        { error -> return@runBlocking "ERROR $error" }
                    )
            }
            if (response.startsWith("ERROR")) {
                throw Exception(response)
            }
            return Gson().fromJson(response, T2::class.java)
        }

        inline fun <T1, reified T2> sendHttpGetRequest(url: String, data: T1, token: String? = null): T2 {
            var response = runBlocking {
                var headers = emptyMap<String, String>()
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token")
                }
                var query = ""
                if(data != null) {
                    val dataMap = MapHelper.toMapNotNull(data)
                    query = dataMap.entries.stream()
                        .map { (k, v) ->
                            "${URLEncoder.encode(k, "utf-8")}=${
                                URLEncoder.encode(
                                    v.toString(),
                                    "utf-8"
                                )
                            }"
                        }
                        .reduce { p1, p2 -> "$p1&$p2" }
                        .orElse("")
                }
                val (request, response, result) = Fuel.get("$url?$query")
                    .header(headers)
                    .awaitStringResponseResult()
                result.fold(
                    { data -> return@runBlocking data },
                    { error -> return@runBlocking "ERROR $error" }
                )
            }
            if (response.startsWith("ERROR")) {
                throw Exception(response)
            }
            return Gson().fromJson(response, T2::class.java)
        }

        inline fun <reified T> sendHttpPutRequest(url: String, data: T, token: String? = null) : T {
            var response = runBlocking {
                var headers = emptyMap<String, String>()
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token")
                }
                val (request, response, result) = Fuel.put(url)
                    .header(headers)
                    .jsonBody(Gson().toJson(data))
                    .awaitStringResponseResult()
                result.fold(
                    { data -> return@runBlocking data },
                    { error -> return@runBlocking "ERROR $error" }
                )
            }
            if (response.startsWith("ERROR")) {
                throw Exception(response)
            }
            return Gson().fromJson(response, T::class.java)
        }

        inline fun sendHttpDeleteRequest(url: String, token: String? = null) : Boolean {
            var response = runBlocking {
                var headers = emptyMap<String, String>()
                if(token != null) {
                    headers = mapOf("Authorization" to "Bearer $token")
                }
                val (request, response, result) = Fuel.delete(url)
                    .header(headers)
                    .awaitStringResponseResult()
                result.fold(
                    { _ -> return@runBlocking "ok" },
                    { error -> return@runBlocking "ERROR $error" }
                )
            }
            if (response.startsWith("ERROR")) {
                throw Exception(response)
            }
            return true
        }
    }
}
