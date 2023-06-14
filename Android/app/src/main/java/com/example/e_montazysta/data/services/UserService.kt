package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.ui.user.UserDAO
import com.example.e_montazysta.ui.user.UserFilterDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserService {
    @GET("/api/v1/users/filter")
    suspend fun getListOfUsers(
        @Header("Authorization") token: String
    ): List<UserFilterDAO>

    @GET("/api/v1/users/{id}")
    suspend fun getUserDetail(@Header("Authorization") token: String, @Path("id") id: Int): UserDAO

    @GET("/api/v1/aboutme")
    suspend fun getAboutMe(@Header("Authorization") token: String): CurrentUser
}