package com.example.e_montazysta.helpers.Interfaces

import com.example.e_montazysta.data.model.User

interface IUserAdapter {
    fun toJson(users: List<User>): List<Int>
    fun fromJson(userId: Int): User
}