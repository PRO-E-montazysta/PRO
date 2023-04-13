package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ToolService {
    @GET("/tools/filter")
    @Headers("Cache-Control: no-cache")
    suspend fun getTools(
    ): List<ToolDAO>
}