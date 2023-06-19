package com.example.e_montazysta.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext


class UserListViewModel(private val repository: IUserRepository) : ViewModel(), CoroutineScope,
    KoinComponent {

    private var job: Job? = null

    private val _usersLiveData = MutableLiveData<List<UserListItem>>()
    val users: LiveData<List<UserListItem>> = _usersLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _warehousesLiveData = MutableLiveData<List<WarehouseFilterDAO>>()
    val warehouseLiveData: LiveData<List<WarehouseFilterDAO>> = _warehousesLiveData

    private val _filterLiveData = MutableLiveData<Map<String, String>>()
    val filterLiveData: LiveData<Map<String, String>> = _filterLiveData

    private val _isEmptyLiveData = MutableLiveData<Boolean>()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getFilterUsers() {
        job = launch {
            val payload = _filterLiveData.value
            getFilterUsersAsync(payload)
        }
    }

    private suspend fun getFilterUsersAsync(payload: Map<String, String>?) {
        _isLoadingLiveData.postValue(true)
        _isEmptyLiveData.postValue(false)
        val result = repository.getFilterUsers(payload)
        when (result) {
            is Result.Success -> {
                _usersLiveData.postValue(result.data)
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
