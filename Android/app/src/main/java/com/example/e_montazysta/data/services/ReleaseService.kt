package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.release.ReleaseDAO
import retrofit2.http.*


interface ReleaseService {

    @GET("/api/v1/tool-releases/all")
    @Headers("Cache-Control: no-cache")
    suspend fun getRelease(@Header("Authorization") token: String
    ): List<ReleaseDAO>
}