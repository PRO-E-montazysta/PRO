package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.ui.location.LocationDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LocationService {
    @GET("/api/v1/locations/{id}")
    suspend fun getToolType(@Header("Authorization") token: String, @Path("id") id: Int): LocationDAO
}