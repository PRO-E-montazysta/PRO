package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolType
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ToolTypeService {
    @GET("/api/v1/tooltype/{id}")
    suspend fun getToolType(@Header("Authorization") token: String, @Path("id") id: Int): ToolType
}