package com.example.e_montazysta.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class QRScanViewModel : ViewModel(), KoinComponent, CoroutineScope {

    private var job: Job? = null

    private val toolRepository: IToolRepository by inject()
    private val elementRepository: IElementRepository by inject()

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getToolByCode(code: String): Tool? {
        return runBlocking {
            async { getToolByCodeAsync(code) }.await()
        }
    }

    suspend fun getToolByCodeAsync(code: String): Tool? {
        val result = toolRepository.getToolByCode(code)
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                null
            }
        }
    }


    fun getElementByCode(code: String): Element? {
        return runBlocking {
             async { getElementByCodeAsync(code) }.await()
        }
    }

    suspend fun getElementByCodeAsync(code: String): Element? {
        val result = elementRepository.getElementByCode(code)
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                null
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}
