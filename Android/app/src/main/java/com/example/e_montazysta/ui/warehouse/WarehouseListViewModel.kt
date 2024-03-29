package com.example.e_montazysta.ui.warehouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class WarehouseListViewModel(private val repository: IWarehouseRepository) : ViewModel(),
    CoroutineScope {

    private var job: Job? = null

    private val _warehouseLiveData = MutableLiveData<List<WarehouseFilterDAO>>()
    val warehouse: LiveData<List<WarehouseFilterDAO>> = _warehouseLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _filterLiveData = MutableLiveData<Map<String, String>>()
    val filterLiveData: LiveData<Map<String, String>> = _filterLiveData

    private val _isEmptyLiveData = MutableLiveData<Boolean>()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getWarehouses() {
        job = launch {
            getWarehousesAsync()
        }
    }

    private suspend fun getWarehousesAsync() {
        _isLoadingLiveData.postValue(true)
        _isEmptyLiveData.postValue(false)
        val result = repository.getListOfWarehouse()
        when (result) {
            is Result.Success -> {
                _warehouseLiveData.postValue(result.data)
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
