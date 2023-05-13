package com.example.e_montazysta.ui.elementlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ElementsListViewModel(private val repository: IToolRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _elementsLiveData = MutableLiveData<List<ElementListItem>>()
    val elements: LiveData<List<ElementListItem>> = _elementsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getTools() {
        job = launch {
            getToolsAsync()
        }
    }

    private suspend fun getToolsAsync() {
        _isLoadingLiveData.postValue(true)
            val result = repository.getTools()
            when (result) {
                is Result.Success -> _elementsLiveData.postValue(result.data.map { it.mapToToolItem() })
                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}