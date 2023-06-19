package com.example.e_montazysta.ui.element

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ElementsListViewModel(private val repository: IElementRepository) : ViewModel(),
    CoroutineScope {

    private var job: Job? = null

    private val _elementsLiveData = MutableLiveData<List<ElementListItem>>()
    val elements: LiveData<List<ElementListItem>> = _elementsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _isEmptyLiveData = MutableLiveData<Boolean>()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getElements() {
        job = launch {
            getElementsAsync()
        }
    }

    private suspend fun getElementsAsync() {
        _isLoadingLiveData.postValue(true)
        _isEmptyLiveData.postValue(false)
        val result = repository.getElements()
        when (result) {
            is Result.Success -> {
                _elementsLiveData.postValue(result.data.map { it.mapToElementItem() })
                if (result.data.isNullOrEmpty()) _isEmptyLiveData.postValue(true)
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
}
