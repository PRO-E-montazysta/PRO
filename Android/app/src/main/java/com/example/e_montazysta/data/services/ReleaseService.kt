package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.release.ElementReleaseRequest
import com.example.e_montazysta.ui.release.ReleaseDAO
import com.example.e_montazysta.ui.release.ToolReleaseRequest
import com.example.e_montazysta.ui.stage.StageDAO
import retrofit2.http.*


interface ReleaseService {

    @GET("/api/v1/tool-releases/filter")
    suspend fun getRelease(@Header("Authorization") token: String
    ): List<ReleaseDAO>

    @GET("/api/v1/tool-releases/{id}")
    suspend fun getReleaseDetail(@Header("Authorization") token: String,
                                 @Path("id") id: Int): ReleaseDAO

    @PUT("/api/v1/order-stages/releaseTools/{id}")
    suspend fun createToolsRelease(@Header("Authorization") token: String,
                                   @Path("id") stageId: Int,
                                   @Body tools: List<ToolReleaseRequest>
    ): StageDAO

    @PUT("/api/v1/order-stages/releaseElements/{id}/{warehouseId}")
    suspend fun createElementsRelease(@Header("Authorization") token: String,
                                      @Path("id") stageId: Int,
                                      @Path("warehouseId") warehouseId: Int,
                                      @Body elements: List<ElementReleaseRequest>
    ): StageDAO
}
