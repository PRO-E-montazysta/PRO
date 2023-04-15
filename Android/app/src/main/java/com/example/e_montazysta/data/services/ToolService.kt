package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolDAO
import okhttp3.RequestBody
import retrofit2.http.*

interface ToolService {
    @GET("/api/v1/tools/filter")
    @Headers("Cache-Control: no-cache")
    suspend fun getTools(@Header("Authorization") token: String, @Query("warehouse_Id") one: String = "1"
    ): List<ToolDAO>
}