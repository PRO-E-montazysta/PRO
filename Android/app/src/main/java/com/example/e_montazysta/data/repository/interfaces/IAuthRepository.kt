package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.LoggedInUser
import com.example.e_montazysta.data.model.Result

interface IAuthRepository {
    fun login(login: String, password: String) : Result<LoggedInUser>
}