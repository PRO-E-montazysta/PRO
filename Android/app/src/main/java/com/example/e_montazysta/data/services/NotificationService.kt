package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.notification.NotificationDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationService {
    @GET("/api/v1/notifications")
    suspend fun getListOfNotifications(
        @Header("Authorization") token: String
    ): List<NotificationDAO?>

    @PUT("/api/v1/notifications/{id}")
    suspend fun readNotification(
        @Header("Authorization") token: String, @Path("id") id: Int
    ): NotificationDAO
}