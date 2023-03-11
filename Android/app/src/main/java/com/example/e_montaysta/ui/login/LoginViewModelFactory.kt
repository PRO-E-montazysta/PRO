package com.example.e_montaysta.ui.login

import Controllers.AuthController
import Repositories.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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