package com.example.e_montazysta.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_montazysta.data.controllers.AuthController
import com.example.e_montazysta.data.repository.AuthRepository
import com.example.e_montazysta.ui.viewmodels.LoginViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = AuthController(
                    repository = AuthRepository()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
