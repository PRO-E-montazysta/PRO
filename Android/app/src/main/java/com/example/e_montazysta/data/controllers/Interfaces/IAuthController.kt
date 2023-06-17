package com.example.e_montazysta.data.controllers.Interfaces

import com.example.e_montazysta.data.model.LoggedInUser
import com.example.e_montazysta.data.model.Result

interface IAuthController {
    fun login(login: String, password: String): Result<LoggedInUser>
}