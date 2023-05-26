package com.example.e_montazysta.ui.element

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ElementDetailViewModel(private val repository: IElementRepository) : ViewModel(), CoroutineScope {
    private var job: Job? = null

    private val _elementDetailLiveData = MutableLiveData<Element>()
    val elementdetail: LiveData<Element> = _elementDetailLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getElementDetail(id: Int) {
        job = launch {
            getElementDetailAsync(id)
        }
    }

    private suspend fun getElementDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getElementDetails(id)
        when (result) {
            is Result.Success -> _elementDetailLiveData.postValue(result.data)
            is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
        }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}