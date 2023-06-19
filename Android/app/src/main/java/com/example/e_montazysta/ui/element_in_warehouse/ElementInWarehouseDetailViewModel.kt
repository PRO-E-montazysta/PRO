package com.example.e_montazysta.ui.element_in_warehouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IElementInWarehouseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ElementInWarehouseDetailViewModel(private val repository: IElementInWarehouseRepository) :
    ViewModel(), CoroutineScope {
    private var job: Job? = null

    private val _elementDetailLiveData = MutableLiveData<ElementInWarehouseDAO>()
    val elementDetail: LiveData<ElementInWarehouseDAO> = _elementDetailLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getElementInWarehouseDetail(id: Int) {
        job = launch {
            getElementInWarehouseDetailAsync(id)
        }
    }

    private suspend fun getElementInWarehouseDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getElementInWarehouseDetails(id)
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