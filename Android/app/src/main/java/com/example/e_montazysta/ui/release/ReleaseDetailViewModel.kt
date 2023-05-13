package com.example.e_montazysta.ui.release

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.IReleaseRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ReleaseDetailViewModel(private val repository: IReleaseRepository) : ViewModel(), CoroutineScope {
    private var job: Job? = null

    private val _releaseDetailLiveData = MutableLiveData<Release>()
    val releasedetail: LiveData<Release> = _releaseDetailLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getReleaseDetail(id: Int) {
        job = launch {
            getReleaseDetailAsync(id)
        }
    }

    private suspend fun getReleaseDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getReleaseDetail(id)
        when (result) {
            is Result.Success -> _releaseDetailLiveData.postValue(result.data)
            is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
        }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}