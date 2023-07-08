package com.example.e_montazysta.ui.stage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.repository.interfaces.IStageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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

    fun setStageDetail(stage: Stage) {
        _stageDetailLiveData.postValue(stage)
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

    fun nextOrderStatus() {
        job = launch {
            nextOrderStatusAsync()
        }
    }

    private suspend fun nextOrderStatusAsync() {
        val stage = stagedetail.value
        _isLoadingLiveData.postValue(true)
        stagedetail.value?.let {
            if(stage?.status == StageStatus.RETURN) {
                if (it.simpleToolReleases.none { tool -> tool.returnTime == null }) {
                    val result = repository.nextOrderStatus(it.id)
                    when (result) {
                        is Result.Success -> {
                            _stageDetailLiveData.postValue(result.data)
                            _messageLiveData.postValue("Pomyślnie zmianiono status na ${stagedetail.value!!.status.value}")
                        }

                        is Result.Error -> {
                            result.exception.message?.let { _messageLiveData.postValue(it) }
                            _isLoadingLiveData.postValue(false)
                        }
                    }
                } else {
                    _messageLiveData.postValue("Aby przejść do następnego etapu wszystkie narzędzia muszą zostać zwrócone.")
                }
            } else {
                val result = repository.nextOrderStatus(it.id)
                when (result) {
                    is Result.Success -> {
                        _stageDetailLiveData.postValue(result.data)
                        _messageLiveData.postValue("Pomyślnie zmianiono status na ${stagedetail.value!!.status.value}")
                    }

                    is Result.Error -> {
                        result.exception.message?.let { _messageLiveData.postValue(it) }
                        _isLoadingLiveData.postValue(false)
                    }
                }
            }
        }
        _isLoadingLiveData.postValue(false)
    }

}
