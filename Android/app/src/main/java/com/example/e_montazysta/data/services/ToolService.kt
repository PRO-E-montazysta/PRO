package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.ToolDAO
import retrofit2.http.*

interface ToolService {
    @GET("/api/v1/tools/filter")
    @Headers("Cache-Control: no-cache")
    suspend fun getTools(@Header("Authorization") token: String,
                         @Query("name") name : String? = null,
                         @Query("code") code : String? = null,
                         @Query("warehouse_Id") warehouse: String? = null,
                         @Query("toolType_Id") toolTypeId : String? = null
    ): List<ToolDAO>
}