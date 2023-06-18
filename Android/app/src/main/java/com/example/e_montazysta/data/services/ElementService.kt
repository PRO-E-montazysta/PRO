package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ElementDAO
import retrofit2.http.*

interface ElementService {
    @GET("/api/v1/elements/filter")
    suspend fun getElements(
        @Header("Authorization") token: String,
        @Query("name") name: String? = null,
        @Query("code") code: String? = null,
        @Query("warehouse_Id") warehouse: String? = null,
        @Query("toolType_Id") toolTypeId: String? = null
    ): List<ElementDAO>

    @GET("/api/v1/elements/bycode/{code}")
    suspend fun getElementByCode(
        @Header("Authorization") token: String,
        @Path("code") code: String?
    ): ElementDAO

    @GET("/api/v1/elements/{id}")
    suspend fun getElementById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ElementDAO
}