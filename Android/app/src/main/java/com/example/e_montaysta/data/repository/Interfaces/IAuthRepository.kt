package com.example.e_montaysta.data.repository.Interfaces

import com.example.e_montaysta.data.model.Result
import com.example.e_montaysta.data.model.LoggedInUser

interface IAuthRepository {
    fun login(login: String, password: String) : Result<LoggedInUser>
}