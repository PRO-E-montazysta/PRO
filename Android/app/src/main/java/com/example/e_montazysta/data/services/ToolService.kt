package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolDAO
import com.example.e_montazysta.ui.toollist.ToolListItem
import retrofit2.http.*

interface ToolService {
    @GET("/api/v1/tools/filter")
    suspend fun getFilterTools(@Header("Authorization") token: String): List<ToolListItem>

    @GET("/api/v1/tools/filter")
    suspend fun getFilterTools(
        @Header("Authorization") token: String,
        @QueryMap(encoded = true) payload: Map<String, String>?
    ): List<ToolListItem>

    @GET("/api/v1/tools/bycode/{code}")
    suspend fun getToolByCode(
        @Header("Authorization") token: String,
        @Path("code") code: String?
    ): ToolDAO

    @GET("/api/v1/tools/{id}")
    suspend fun getToolDetails(@Header("Authorization") token: String, @Path("id") id: Int): ToolDAO
}
