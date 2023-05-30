package com.example.e_montazysta.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.INotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class NotificationListViewModel(private val repository: INotificationRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _notificationLiveData = MutableLiveData<List<Notification?>>()
    val notification: LiveData<List<Notification?>> = _notificationLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _notificationsNumberLiveData = MutableLiveData<Int>()
    val notificationsNumberLiveData: LiveData<Int> = _notificationsNumberLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getNotification() {
        job = launch {
            async { getNotificationAsync() }
        }

    }

    private suspend fun getNotificationAsync() {
        _isLoadingLiveData.postValue(true)
            val result = repository.getListOfNotifications()
            when (result) {
                is Result.Success -> {
                    _notificationLiveData.postValue(result.data)
                    _notificationsNumberLiveData.postValue(result.data.size)
                }
                is Result.Error -> {
                    result.exception.message?.let { _messageLiveData.postValue(it) }
                    _isLoadingLiveData.postValue(false)
                }
            }
        _isLoadingLiveData.postValue(false)
    }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

    fun readNotification(notification: Notification) {
        job = launch {
            readNotificationAsync(notification)
        }
    }

    fun readNotification(notifications: List<Notification?>) {
        job = launch {
            readNotificationAsync(notifications)
        }
    }
    private suspend fun  readNotificationAsync(notifications: List<Notification?>) {
        _isLoadingLiveData.postValue(true)
        notifications.forEach{
            val result = repository.readNotification(it!!.id)
            when (result) {
                is Result.Success -> assert(it.id == result.data.id){ _messageLiveData.postValue("Błąd!") }
                is Result.Error -> {
                    result.exception.message?.let { _messageLiveData.postValue(it) }
                    _isLoadingLiveData.postValue(false)
                }
            }
        }
        getNotificationAsync()
        _isLoadingLiveData.postValue(false)
    }
    private suspend fun readNotificationAsync(notification: Notification) {
        _isLoadingLiveData.postValue(true)
        val result = repository.readNotification(notification.id)
        when (result) {
            is Result.Success -> getNotificationAsync()
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                _isLoadingLiveData.postValue(false)
            }
        }
        _isLoadingLiveData.postValue(false)
    }
}
