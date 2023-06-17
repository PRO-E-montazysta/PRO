package com.example.e_montazysta.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.INotificationRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.notification.Notification
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class NotificationRepository(private val serviceProvider: IServiceProvider) :
    INotificationRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    private val notificationService = serviceProvider.getNotificationService()

    override suspend fun getListOfNotifications(): Result<List<Notification?>> {
        return try {
            val notificationDAOs = notificationService.getListOfNotifications(token)
            val notifications = notificationDAOs.map { it?.mapToNotification() }
            Result.Success(notifications)
        } catch (e: HttpException) {
            Log.e(TAG, e.message!!)
            Result.Error(e)
        }
    }

    override suspend fun readNotification(id: Int): Result<Notification> {
        return try {
            val notificationDAO = notificationService.readNotification(token, id)
            val notifications = notificationDAO.mapToNotification()
            Result.Success(notifications)
        } catch (e: HttpException) {
            Log.e(TAG, e.message!!)
            Result.Error(e)
        }
    }

}