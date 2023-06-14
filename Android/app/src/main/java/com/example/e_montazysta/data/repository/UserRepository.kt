package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.user.UserListItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository (private val serviceProvider: IServiceProvider): IUserRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()

    override suspend fun getFilterUsers(payload: Map<String, String>?): Result<List<UserListItem>> {
        return try {
            val userService = serviceProvider.getUserService()
            val usersDAOs = userService.getListOfUsers(token)
            val users = usersDAOs.map { it.mapToUserItem() }
            Result.Success(users)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUserDetails(id: Int): Result<User> {
        return try {
            val userService = serviceProvider.getUserService()
            val userDAO = userService.getUserDetail(token, id)
            val user = userDAO.mapToUser()
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAboutMe(): Result<CurrentUser> {
        return try {
            val userService = serviceProvider.getUserService()
            val currentUser = userService.getAboutMe(token)
            Result.Success(currentUser)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}