package com.example.e_montazysta.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

class DashboardViewModel(
    private val userRepository: IUserRepository,
) : ViewModel(),
    KoinComponent,
    CoroutineScope {

    private var job: Job? = null

    private val _currentUserLiveData = MutableLiveData<CurrentUser>()
    val currentUser: LiveData<CurrentUser> get() = _currentUserLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun getCurrentUser() {
        job = launch {
            getCurrentUserAsync()
        }
    }

    private suspend fun getCurrentUserAsync() {
        _isLoadingLiveData.postValue(true)
        val result = userRepository.getAboutMe()
        when (result) {
            is Result.Success -> {
                val currentUser = result.data
                _currentUserLiveData.postValue(currentUser)
            }

            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                _isLoadingLiveData.postValue(false)
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}