package com.example.e_montazysta.ui.toollist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ToolsListViewModel(private val repository: IToolRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _toolsLiveData = MutableLiveData<List<ToolListItem>>()
    val tools: LiveData<List<ToolListItem>> = _toolsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getFilterTools() {
        job = launch {
            getFilterToolsAsync()
        }
    }

    private suspend fun getFilterToolsAsync() {
        _isLoadingLiveData.postValue(true)
            val result = repository.getFilterTools()
            when (result) {
                is Result.Success -> _toolsLiveData.postValue(result.data)
                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}
