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
                is Result.Success -> _notificationLiveData.postValue(result.data)
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

    fun readNotification(id: Int) {
        job = launch {
            readNotificationAsync(id)
        }
    }

    private suspend fun readNotificationAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.readNotification(id)
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
