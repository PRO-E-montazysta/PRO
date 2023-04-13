package com.example.e_montazysta.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.ui.toollist.ToolListItem
import com.example.e_montazysta.ui.toollist.mapToToolItem
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ToolsViewModel(private val repository: IToolRepository) : ViewModel(), CoroutineScope{

    private val _toolsLiveData = MutableLiveData<List<ToolListItem>>()
    val tools: LiveData<List<ToolListItem>> = _toolsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    suspend fun getTools() {
        _isLoadingLiveData.postValue(true)
        launch {
            val result = repository.getTools()
            _isLoadingLiveData.postValue(false)
            when (result) {
                is Result.Success -> _toolsLiveData.postValue(result.data.map { it.mapToToolItem() })
                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}