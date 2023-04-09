package com.example.e_montaysta.data.repository.Interfaces

import com.example.e_montaysta.data.model.LoggedInUser
import com.example.e_montaysta.data.model.Result
import com.example.e_montaysta.data.model.Tool

interface IToolRepository {
    fun create(login: String, password: String) : Result<Tool>
    fun get(login: String, password: String) : Result<Tool>
    fun update() : Result<Tool>
    fun delete() : Result<Tool>

}