package com.example.e_montazysta.ui.stage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IStageRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class StageDetailViewModel(private val repository: IStageRepository) : ViewModel(), CoroutineScope {
    private var job: Job? = null

    private val _stageDetailLiveData = MutableLiveData<Stage>()
    val stagedetail: LiveData<Stage> = _stageDetailLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getStageDetail(id: Int) {
        job = launch {
            getStageDetailAsync(id)
        }
    }

    private suspend fun getStageDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getStageDetails(id)
        when (result) {
            is Result.Success -> _stageDetailLiveData.postValue(result.data)
            is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
        }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}