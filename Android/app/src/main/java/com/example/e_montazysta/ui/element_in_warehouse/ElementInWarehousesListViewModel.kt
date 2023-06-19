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


class ElementInWarehousesListViewModel(private val repository: IElementInWarehouseRepository) :
    ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _elementsLiveData = MutableLiveData<List<ElementInWarehousesDAOItem>>()
    val elements: LiveData<List<ElementInWarehousesDAOItem>> = _elementsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getElementInWarehouses(id: Int) {
        job = launch {
            getElementInWarehousesAsync(id)
        }
    }

    private suspend fun getElementInWarehousesAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getElementInWarehouses(id.toString())
        when (result) {
            is Result.Success -> _elementsLiveData.postValue(result.data)
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
