package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.notification.Notification
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationService {
    @GET("/api/v1/notifications")
    suspend fun getListOfNotifications(@Header("Authorization") token: String
    ) : List<Notification?>
}