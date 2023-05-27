package com.example.e_montazysta.ui.stage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IStageRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class StageListViewModel(private val repository: IStageRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _stageLiveData = MutableLiveData<List<StageListItem>>()
    val stage: LiveData<List<StageListItem>> = _stageLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getStage() {
        job = launch {
            getStageAsync()
        }
    }

    private suspend fun getStageAsync() {
        _isLoadingLiveData.postValue(true)
            val result = repository.getListOfStages()
            when (result) {
                is Result.Success -> _stageLiveData.postValue(result.data.map { it.mapToStageItem() })
                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}