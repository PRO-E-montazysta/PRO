package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository (private val serviceProvider: IServiceProvider): IUserRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()

    override suspend fun getListOfUsers(): Result<List<User>> {
        return try {
            val userService = serviceProvider.getUserService()
            val users = userService.getListOfUsers(token)
            Result.Success(users)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUserDetails(id: Int): Result<User> {
        return try {
            val userService = serviceProvider.getUserService()
            val user = userService.getUserDetail(token, id)
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