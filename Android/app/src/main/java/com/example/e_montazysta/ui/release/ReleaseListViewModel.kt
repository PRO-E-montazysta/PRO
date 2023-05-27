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
                is Result.Error -> {
                    result.exception.message?.let { _messageLiveData.postValue(it) }
                    _isLoadingLiveData.postValue(false)
                }
            }
       _isLoadingLiveData.postValue(false)
    }

    fun setReleaseList(stage: Stage){
        _isLoadingLiveData.postValue(true)
        var releases: List<ReleaseListItem> = mutableListOf()
        val temp = stage.toolReleases
        releases = releases + (stage.toolReleases.map { it!!.mapToReleaseItem() })
        if (!stage.elementReturnReleases.isNullOrEmpty()){
            releases += (stage.elementReturnReleases.map { it.mapToReleaseItem() })
        }
        releases.sortedBy { it.id }
        _releaseLiveData.postValue(releases)
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}
