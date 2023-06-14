package com.example.e_montazysta.ui.user

import android.util.Log
import android.util.Log.getStackTraceString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class UserDetailViewModel(private val repository: IUserRepository) : ViewModel(), CoroutineScope {

    private var job: Job? = null

    private val _eventLiveData = MutableLiveData<User>()
    val user: LiveData<User> = _eventLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getUserDetail(id: Int) {
        job = launch {
            getUserDetailAsync(id)
        }
    }

    private suspend fun getUserDetailAsync(id: Int) {
        _isLoadingLiveData.postValue(true)
        val result = repository.getUserDetails(id)
        when (result) {
            is Result.Success -> _eventLiveData.postValue(result.data)
            is Result.Error -> {
                Log.e("getUser", getStackTraceString(result.exception))
                result.exception.message?.let { _messageLiveData.postValue(it) }
                _isLoadingLiveData.postValue(false)
            }
            }
        _isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}