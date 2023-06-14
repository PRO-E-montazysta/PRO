package com.example.e_montazysta.data.model

import android.content.ContentValues.TAG
import android.util.Log
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.example.e_montazysta.ui.user.Role
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val pesel: String?,
    val roles: List<Role>,
    val email: String,
    val phone: String
) {
    override fun toString(): String = "$firstName $lastName"

    companion object: KoinComponent {
        suspend fun getUserDetails(userId: Int): User? {
            val userRepository: IUserRepository by inject()
            val result = userRepository.getUserDetails(userId)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> {
                    Log.e(TAG, Log.getStackTraceString(result.exception))
                    null
                }
            }
        }
    }
}
