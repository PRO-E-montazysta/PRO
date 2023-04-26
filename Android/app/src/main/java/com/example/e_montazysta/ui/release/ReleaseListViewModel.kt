package com.example.e_montazysta.ui.release

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.IReleaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Job


class ReleaseListViewModel(private val repository: IReleaseRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _releaseLiveData = MutableLiveData<List<ReleaseListItem>>()
    val release: LiveData<List<ReleaseListItem>> = _releaseLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getRelease() {
        job = launch {
            getReleaseAsync()
        }
    }

    private suspend fun getReleaseAsync() {
        _isLoadingLiveData.postValue(true)
            val result = repository.getRelease()
            when (result) {
                is Result.Success -> _releaseLiveData.postValue(result.data.map { it.mapToReleaseItem() })
                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}