package com.example.e_montaysta.data.controllers

import com.example.e_montaysta.data.controllers.Interfaces.IAuthController
import com.example.e_montaysta.data.repository.Interfaces.IAuthRepository
import com.example.e_montaysta.data.model.Result
import com.example.e_montaysta.data.model.LoggedInUser

class AuthController(private val repository: IAuthRepository) : IAuthController {

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    override fun login(login: String, password: String) : Result<LoggedInUser> {
        val result = repository.login(login, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }
    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}