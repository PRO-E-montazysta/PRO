package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User

interface IToolTypeRepository {
    suspend fun getListOfUsers() : Result<List<User>>
    suspend fun getUserDetails(id: Int): Result<User>
    suspend fun getAboutMe() : Result<CurrentUser>
}
