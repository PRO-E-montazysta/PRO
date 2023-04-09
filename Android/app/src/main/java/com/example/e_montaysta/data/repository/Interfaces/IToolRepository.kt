package com.example.e_montaysta.data.repository.Interfaces

import com.example.e_montaysta.data.model.Tool
import retrofit2.Response

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface IToolRepository {
    @GET("/tools/{id}")
    suspend fun getDetails(id: Long) : Response<Tool>
    @GET("/tools/filter")
    suspend fun getFilter(tool: Tool) : Response<List<Tool>>
    @GET("/tools/all")
    suspend fun getTools() : Response<List<Tool>>
    @PUT("/tools/{id}")
    suspend fun update(tool: Tool)
    @POST("/tools")
    suspend fun create(tool: Tool)
    @DELETE("/tools/{id}")
    suspend fun delete(tool: Tool)

}