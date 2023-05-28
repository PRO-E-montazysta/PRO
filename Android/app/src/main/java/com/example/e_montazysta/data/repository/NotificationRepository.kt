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

class NotificationRepository (private val serviceProvider: IServiceProvider): INotificationRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    override suspend fun getListOfNotifications(): Result<List<Notification?>> {
        return try {
            val notificationService = serviceProvider.getNotificationService()
            val notifications = notificationService.getListOfNotifications(token)
            Result.Success(notifications)
        } catch (e: HttpException) {
            Log.e(TAG, e.message!!)
            throw e
            Result.Error(e)
        }
    }

}