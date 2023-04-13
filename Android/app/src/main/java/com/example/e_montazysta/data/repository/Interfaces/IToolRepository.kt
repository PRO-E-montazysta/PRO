package com.example.e_montazysta.data.repository.Interfaces

import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.model.Result
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface IToolRepository {
    @GET("/tools/filter")
    suspend fun getTools() : Result<List<Tool>>
}