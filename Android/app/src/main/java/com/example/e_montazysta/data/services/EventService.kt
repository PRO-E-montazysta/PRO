package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.ui.event.ElementEventDAO
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
    @GET("/events/filter")
    fun getFilteredEvents(@Header("Authorization") token: String, @QueryMap payload: Map<String, String>): List<Event>

    @GET("/toolEvent/{id}")
    fun getToolEventDetails(@Header("Authorization") token: String, @Path("id") id: Int): ToolEventDAO
    
    @PUT("/toolEvent/{id}")
    fun updateToolEvent(@Header("Authorization") token: String, @Path("id") id: Int, @Body data: Element): ToolEventDAO

    @POST("/toolEvent")
    fun postToolEvent(@Header("Authorization") token: String, @Body data: Element): ToolEventDAO

    @DELETE("/toolEvent/{id}")
    fun deleteToolEvent(@Header("Authorization") token: String, @Path("id") id: String): ToolEventDAO

    @GET("/elementEvent/{id}")
    fun getElementEventDetails(@Header("Authorization") token: String, @Path("id") id: String?): ElementEventDAO

    @PUT("/elementEvent/{id}")
    fun updateElementEvent(@Header("Authorization") token: String, @Path("id") id: String, @Body data: Element): ElementEventDAO

    @POST("/elementEvent")
    fun postElementEvent(@Header("Authorization") token: String, @Body data: Element): ElementEventDAO

    @DELETE("/elementEvent/{id}")
    fun deleteElementEvent(@Header("Authorization") token: String, @Path("id") id: String): ElementEventDAO
}