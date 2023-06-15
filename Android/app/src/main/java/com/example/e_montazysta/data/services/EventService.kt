package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.ui.event.ElementEventDAO
import com.example.e_montazysta.ui.event.FilterEventDAO
import com.example.e_montazysta.ui.event.ToolEventDAO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface EventService {
    @GET("/api/v1/events/filter")
    suspend fun getFilteredEvents(@Header("Authorization") token: String): List<FilterEventDAO>

    @GET("/api/v1/events/filter")
    suspend fun getFilteredEvents(@Header("Authorization") token: String, @QueryMap(encoded = true) payload: Map<String, String>?): List<FilterEventDAO>

    @GET("/api/v1/toolEvent/{id}")
    suspend fun getToolEventDetails(@Header("Authorization") token: String, @Path("id") id: Int): ToolEventDAO
    
    @PUT("/api/v1/toolEvent/{id}")
    suspend fun updateToolEvent(@Header("Authorization") token: String, @Path("id") id: Int, @Body data: Element): ToolEventDAO

    @POST("/api/v1/toolEvent")
    suspend fun postToolEvent(@Header("Authorization") token: String, @Body data: Element): ToolEventDAO

    @DELETE("/api/v1/toolEvent/{id}")
    suspend fun deleteToolEvent(@Header("Authorization") token: String, @Path("id") id: String): ToolEventDAO

    @GET("/api/v1/elementEvent/{id}")
    suspend fun getElementEventDetails(@Header("Authorization") token: String, @Path("id") id: String?): ElementEventDAO

    @PUT("/api/v1/elementEvent/{id}")
    suspend fun updateElementEvent(@Header("Authorization") token: String, @Path("id") id: String, @Body data: Element): ElementEventDAO

    @POST("/api/v1/elementEvent")
    suspend fun postElementEvent(@Header("Authorization") token: String, @Body data: Element): ElementEventDAO

    @DELETE("/api/v1/elementEvent/{id}")
    suspend fun deleteElementEvent(@Header("Authorization") token: String, @Path("id") id: String): ElementEventDAO
}