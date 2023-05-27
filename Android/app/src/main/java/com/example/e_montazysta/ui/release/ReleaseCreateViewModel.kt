package com.example.e_montazysta.ui.release

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext


class ReleaseCreateViewModel() : ViewModel(), CoroutineScope, KoinComponent {

    private var job: Job? = null

    private val toolRepository: IToolRepository by inject()

    private val elementRepository: IElementRepository by inject()

    private val releaseRepository: IReleaseRepository by inject()

    private val _itemsLiveData = MutableLiveData<List<Any>>()
    val itemsLiveData: LiveData<List<Any>> get() = _itemsLiveData

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
        val currentItems = _itemsLiveData.value ?: emptyList()
        val existingItem = currentItems.find { it is Tool && it.code == code }
        if (existingItem != null) {
            _messageLiveData.postValue("Item already added to list:\n$code")
        } else {
            val result = toolRepository.getToolByCode(code)
            when (result) {
                is Result.Success -> {
                    val tool = result.data
                    _itemsLiveData.value = currentItems + tool
                }

                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    fun addElementToRelease(code: String?) {
        job = launch {
            addElementToReleaseAsync(code)
        }
    }

    //TODO Ogarnąć ten kod bo jest 2x to samo co w narzędziu
    private suspend fun addElementToReleaseAsync(code: String?) {
        _isLoadingLiveData.postValue(true)
        val currentItems = _itemsLiveData.value ?: emptyList()
        val existingItem = currentItems.find { it is Element && it.code == code }
        if (existingItem != null) {
            _messageLiveData.postValue("Item already added to list:\n$code")
        } else {
            val result = elementRepository.getElementByCode(code)
            when (result) {
                is Result.Success -> {
                    val element = result.data
                    _itemsLiveData.value = currentItems + element
                }

                is Result.Error -> {
                    result.exception.message?.let { _messageLiveData.postValue(it) }
                    _isLoadingLiveData.postValue(false)
                }
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    suspend fun createRelease(items: List<ReleaseItem>, stageId: Int): Result<Any>? {
        return createReleaseAsync(items, stageId)
    }

    private suspend fun createReleaseAsync(items: List<ReleaseItem>, stageId: Int): Result<Any> {
        _isLoadingLiveData.postValue(true)
        val result = releaseRepository.createRelease(items, stageId)
        when (result) {
            is Result.Success -> {
                _messageLiveData.postValue("Wydanie utworzone!")
            }
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                _isLoadingLiveData.postValue(false)
            }
        }
        _isLoadingLiveData.postValue(false)
        return result
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}