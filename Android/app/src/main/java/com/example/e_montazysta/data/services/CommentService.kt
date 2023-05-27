package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.model.CommentDAO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommentService {
    @GET("/api/v1/comments/all")
    suspend fun getListOfComments(@Header("Authorization") token: String
    ): List<CommentDAO>
    @GET("/api/v1/comments/{id}")
    suspend fun getCommentDetail(@Header("Authorization") token: String, @Path("id") id: Int): CommentDAO
}