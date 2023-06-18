package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.ui.user.UserListItem

interface IUserRepository {
    suspend fun getFilterUsers(payload: Map<String, String>?): Result<List<UserListItem>>
    suspend fun getUserDetails(id: Int): Result<User>
    suspend fun getAboutMe(): Result<CurrentUser>
}
