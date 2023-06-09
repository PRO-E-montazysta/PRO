package com.example.e_montazysta.ui.toollist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import com.example.e_montazysta.data.repository.interfaces.IToolTypeRepository
import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext


class ToolsListViewModel(private val repository: IToolRepository) : ViewModel(), CoroutineScope, KoinComponent {

    private var job: Job? = null

    private val _toolsLiveData = MutableLiveData<List<ToolListItem>>()
    val tools: LiveData<List<ToolListItem>> = _toolsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _toolTypesLiveData = MutableLiveData<List<ToolType>>()
    val toolTypesLiveData: LiveData<List<ToolType>> = _toolTypesLiveData

    private val _warehousesLiveData = MutableLiveData<List<WarehouseFilterDAO>>()
    val warehouseLiveData: LiveData<List<WarehouseFilterDAO>> = _warehousesLiveData

    private val _filterLiveData = MutableLiveData<Map<String, String>>()
    val filterLiveData: LiveData<Map<String, String>> = _filterLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getFilterTools() {
        job = launch {
            val payload = _filterLiveData.value
            getFilterToolsAsync(payload)
        }
    }

    private suspend fun getFilterToolsAsync(payload: Map<String, String>?) {
        _isLoadingLiveData.postValue(true)
            val result = repository.getFilterTools(payload)
            when (result) {
                is Result.Success -> _toolsLiveData.postValue(result.data)
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

    fun getListOfToolType() {
        job = launch {
            async { getListOfToolTypeAsync() }
        }
    }

    private suspend fun getListOfToolTypeAsync() {
        _isLoadingLiveData.postValue(true)
        val toolTypeRepository: IToolTypeRepository by inject()
        val result = toolTypeRepository.getListOfToolType()

        when (result) {
            is Result.Success -> _toolTypesLiveData.postValue(result.data)
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                Log.e("getListOfToolTypeAsync", Log.getStackTraceString(result.exception))
                _isLoadingLiveData.postValue(false)

            }
        }
        _isLoadingLiveData.postValue(false)
    }

    fun getListOfWarehouse() {
        job = launch {
            async { getListOfWarehouseAsync() }
        }
    }

    private suspend fun getListOfWarehouseAsync(){
        _isLoadingLiveData.postValue(true)
        val warehouseRepository: IWarehouseRepository by inject()
        val result = warehouseRepository.getListOfWarehouse()

        when (result) {
            is Result.Success -> _warehousesLiveData.postValue(result.data)
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                Log.e("getListOfToolTypeAsync", Log.getStackTraceString(result.exception))
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
}
