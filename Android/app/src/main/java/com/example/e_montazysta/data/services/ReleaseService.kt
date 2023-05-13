package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.release.ReleaseDAO
import retrofit2.http.*


interface ReleaseService {

    @GET("/api/v1/tool-releases/all")
    suspend fun getRelease(@Header("Authorization") token: String
    ): List<ReleaseDAO>

    @GET("/api/v1/tool-releases/{id}")
    suspend fun getReleaseDetail(@Header("Authorization") token: String, @Path("id") id: Int): ReleaseDAO
}