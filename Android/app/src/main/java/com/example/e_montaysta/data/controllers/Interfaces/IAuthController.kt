package com.example.e_montaysta.data.controllers.Interfaces

import com.example.e_montaysta.data.model.LoggedInUser
import com.example.e_montaysta.data.model.Result

interface IAuthController {
    fun login(login: String, password: String) : Result<LoggedInUser>
}