package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.notification.Notification
interface INotificationRepository {
    suspend fun getListOfNotifications() : Result<List<Notification?>>
    suspend fun readNotification(id: Int): Result<Notification>

}
