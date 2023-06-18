package com.example.e_montazysta.data.api

import com.example.e_montazysta.data.model.Tool
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ServiceApi {
    @GET("/tools/{id}")
    suspend fun getDetails(
        id: Int,
        @Header("Authorization") token: String
    ): Response<Tool>

    @GET("/tools/filter")
    suspend fun getTools(
        @Header("Authorization") token: String
    ): Response<List<Tool>>
}