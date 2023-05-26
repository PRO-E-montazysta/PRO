package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolDAO
import retrofit2.http.*

interface ToolService {
    @GET("/api/v1/tools/filter")
    suspend fun getTools(@Header("Authorization") token: String,
                         @Query("name") name : String? = null,
                         @Query("code") code : String? = null,
                         @Query("warehouse_Id") warehouse: String? = null,
                         @Query("toolType_Id") toolTypeId : String? = null
    ): List<ToolDAO>
    @GET("/api/v1/tools/bycode/{code}")
    suspend fun getToolByCode(@Header("Authorization") token: String, @Path("code") code: String?): ToolDAO
}
