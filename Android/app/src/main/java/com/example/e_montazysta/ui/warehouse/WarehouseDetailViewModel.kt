package com.example.e_montazysta.ui.warehouse

import android.util.Log
import android.util.Log.getStackTraceString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Warehouse
import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class WarehouseDetailViewModel(private val repository: IWarehouseRepository) : ViewModel(),
    CoroutineScope {

    private var job: Job? = null

    private val _eventLiveData = MutableLiveData<Warehouse>()
    val tool: LiveData<Warehouse> = _eventLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getWarehouseDetail(id: Int) {
        job = launch {
            getWarehouseDetailAsync(id)
        }
    }

    private suspend fun getWarehouseDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getWarehouseDetails(id)
        when (result) {
            is Result.Success -> _eventLiveData.postValue(result.data)
            is Result.Error -> {
                throw result.exception
                Log.e("getWarehouse", getStackTraceString(result.exception))
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