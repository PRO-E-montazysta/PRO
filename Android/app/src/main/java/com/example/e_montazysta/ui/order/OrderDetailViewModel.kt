package com.example.e_montazysta.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IOrderRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OrderDetailViewModel(private val repository: IOrderRepository) : ViewModel(), CoroutineScope {
    private var job: Job? = null

    private val _orderDetailLiveData = MutableLiveData<Order>()
    val orderdetail: LiveData<Order> = _orderDetailLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getOrderDetail(id: Int) {
        job = launch {
            getOrderDetailAsync(id)
        }
    }

    private suspend fun getOrderDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getOrderDetails(id)
        when (result) {
            is Result.Success -> _orderDetailLiveData.postValue(result.data)
            is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
        }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}