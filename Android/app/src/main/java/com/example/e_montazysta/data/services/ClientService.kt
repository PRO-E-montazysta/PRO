package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.client.ClientDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ClientService {
    @GET("/api/v1/clients/{id}")
    suspend fun getClient(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ClientDAO
}