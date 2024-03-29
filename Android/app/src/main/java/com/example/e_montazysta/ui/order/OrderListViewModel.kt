package com.example.e_montazysta.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IOrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class OrderListViewModel(private val repository: IOrderRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _orderLiveData = MutableLiveData<List<OrderListItem>>()
    val order: LiveData<List<OrderListItem>> = _orderLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _isEmptyLiveData = MutableLiveData<Boolean>()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getOrder() {
        job = launch {
            getOrderAsync()
        }
    }

    private suspend fun getOrderAsync() {
        _isLoadingLiveData.postValue(true)
        _isEmptyLiveData.postValue(false)
        val result = repository.getListOfOrders()
        when (result) {
            is Result.Success -> {
                _orderLiveData.postValue(result.data)
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
