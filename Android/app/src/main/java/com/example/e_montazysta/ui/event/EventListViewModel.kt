package com.example.e_montazysta.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class EventListViewModel(private val repository: IEventRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _eventLiveData = MutableLiveData<List<EventListItem>>()
    val event: LiveData<List<EventListItem>> = _eventLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getEvent() {
        job = launch {
            getEventAsync()
        }
    }

    private suspend fun getEventAsync() {
        _isLoadingLiveData.postValue(true)
            val result = repository.getListOfEvents()
            when (result) {
                is Result.Success -> _eventLiveData.postValue(result.data.map { it.mapToEventItem() })
                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}