package com.example.e_montazysta.ui.release

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ReleaseListViewModel(private val repository: IReleaseRepository) : ViewModel(),
    CoroutineScope {

    private var job: Job? = null

    private val _releaseLiveData = MutableLiveData<List<ReleaseListItem>>()
    val release: LiveData<List<ReleaseListItem>> = _releaseLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _isEmptyLiveData = MutableLiveData<Boolean>()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getRelease() {
        job = launch {
            getReleaseAsync()
        }
    }

    private suspend fun getReleaseAsync() {
        _isLoadingLiveData.postValue(true)
        _isEmptyLiveData.postValue(false)
        val result = repository.getRelease()
        when (result) {
            is Result.Success -> {
                _releaseLiveData.postValue(result.data.map { it.mapToReleaseItem() })
                if (result.data.isNullOrEmpty()) _isEmptyLiveData.postValue(true)
            }

            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                _isLoadingLiveData.postValue(false)
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    fun setReleaseList(stage: Stage) {
        job = launch {
            _isLoadingLiveData.postValue(true)
            var releases: List<ReleaseListItem> = mutableListOf()
            releases = releases + (stage.simpleToolReleases.map { it.maptoReleaseListItem() })
            if (!stage.simpleElementReturnReleases.isNullOrEmpty()) {
                releases += (stage.simpleElementReturnReleases.map { it.maptoReleaseListItem() })
            }
            releases.sortedBy { it.releaseTime }
            _releaseLiveData.postValue(releases)
            _isLoadingLiveData.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}
