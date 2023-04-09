package com.example.e_montaysta.data.controllers.Interfaces

import com.example.e_montaysta.data.model.Result
import com.example.e_montaysta.data.model.LoggedInUser

interface IAuthController {
    fun login(login: String, password: String) : Result<LoggedInUser>
}