package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.stage.plannedElementDAO
import com.example.e_montazysta.ui.stage.plannedToolDAO
import retrofit2.http.*

interface PlannedItemService {
    @GET("/api/v1/tools-planned/{id}")
    suspend fun getPlannedTool(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): plannedToolDAO

    @GET("/api/v1/elements-planned/{id}")
    suspend fun getPlannedElement(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): plannedElementDAO
}
