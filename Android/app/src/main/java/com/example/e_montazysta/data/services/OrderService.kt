package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.order.OrderDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OrderService {
    @GET("/api/v1/orders/filter")
    suspend fun getListOfOrders(@Header("Authorization") token: String
    ): List<OrderDAO>

    @GET("/api/v1/orders/{id}")
    suspend fun getOrderDetail(@Header("Authorization") token: String, @Path("id") id: Int): OrderDAO
}