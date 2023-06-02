package com.example.e_montazysta.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class EventListViewModel(private val repository: IEventRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _eventLiveData = MutableLiveData<List<EventListItem>>()
    val event: LiveData<List<EventListItem>> = _eventLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _filterLiveData = MutableLiveData<Map<String, String>>()
    val filterLiveData: LiveData<Map<String, String>> = _filterLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getEvent() {
        job = launch {
            val payload = _filterLiveData.value
            getEventAsync(payload)
        }
    }

    private suspend fun getEventAsync(payload: Map<String, String>?) {
        _isLoadingLiveData.postValue(true)
            val result = repository.getListOfEvents(payload)
            when (result) {
                is Result.Success -> _eventLiveData.postValue( result.data )
                is Result.Error -> {
                    result.exception.message?.let { _messageLiveData.postValue(it) }
                    _isLoadingLiveData.postValue(false)
                }
            }
        _isLoadingLiveData.postValue(false)
    }

    fun filterDataChanged(key: String, value: String?) {
        val filters: MutableMap<String, String> =
            if (!filterLiveData.value.isNullOrEmpty()) filterLiveData.value!!.toMutableMap()
            else mutableMapOf()
        if (!value.isNullOrBlank()) {
            filters[key] = value
        } else {
            filters.remove(key)
        }
        _filterLiveData.value = filters
    }

    fun filterClear() {
        val filters = mutableMapOf<String, String>()
        _filterLiveData.value = filters
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}