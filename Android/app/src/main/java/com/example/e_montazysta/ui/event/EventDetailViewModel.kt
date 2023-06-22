package com.example.e_montazysta.ui.event

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.EventType
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventDetailViewModel(private val repository: IEventRepository) : ViewModel(), CoroutineScope {
    private var job: Job? = null

    private val _eventDetailLiveData = MutableLiveData<Event>()
    val eventdetail: LiveData<Event> = _eventDetailLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getEventDetail(id: Int, type: EventType) {
        job = launch {
            getEventDetailAsync(id, type)
        }
    }

    private suspend fun getEventDetailAsync(id: Int, type: EventType) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getEventDetails(id, type)
        when (result) {
            is Result.Success -> _eventDetailLiveData.postValue(result.data)
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                Log.e(ContentValues.TAG, Log.getStackTraceString(result.exception))
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}