package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.model.Result

interface IUserRepository {
    suspend fun getListOfUsers() : Result<List<User>>
    suspend fun getUserDetails(id: Int): Result<User>
}
