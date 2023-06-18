package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.stage.StageDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface StageService {
    @GET("/api/v1/order-stages/filter")
    suspend fun getListOfStages(
        @Header("Authorization") token: String
    ): List<StageDAO>

    @GET("/api/v1/order-stages/{id}")
    suspend fun getStageDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): StageDAO

    @PUT("/api/v1/order-stages/nextStatus/{id}")
    suspend fun nextOrderStatus(@Header("Authorization") token: String, @Path("id") id: Int): StageDAO
}