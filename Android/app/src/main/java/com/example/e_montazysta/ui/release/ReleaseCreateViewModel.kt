package com.example.e_montazysta.ui.release

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.repository.Interfaces.IReleaseRepository
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.toollist.ToolListItem
import com.example.e_montazysta.ui.toollist.mapToToolItem


class ReleaseCreateViewModel() : ViewModel(), CoroutineScope, KoinComponent {

    private var job: Job? = null

    private val toolRepository: IToolRepository by inject()

    private val _itemsLiveData = MutableLiveData<List<Any>>()
    val itemsLiveData: LiveData<List<Any>> = _itemsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun addToolToRelease(code: String?) {
        job = launch {
            addToolToReleaseAsync(code)
        }
    }

    private suspend fun addToolToReleaseAsync(code: String?) {
        _isLoadingLiveData.postValue(true)
        val result = toolRepository.getToolByCode(code)
        when (result) {
            is Result.Success -> {
                val toolItem = result.data.mapToToolItem()
                val currentItems = _itemsLiveData.value ?: emptyList()
                _itemsLiveData.value = currentItems + toolItem
            }
            is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
        }
        _isLoadingLiveData.postValue(false)
    }

    fun addElementToRelease(code: String?) {
        job = launch {
            addElementToReleaseAsync(code)
        }
    }

    private suspend fun addElementToReleaseAsync(code: String?) {

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}