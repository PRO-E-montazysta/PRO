package com.example.e_montazysta.data.repository.Interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import retrofit2.http.GET

interface IToolRepository {
    @GET("/tools/filter")
    suspend fun getTools() : Result<List<Tool>>
}