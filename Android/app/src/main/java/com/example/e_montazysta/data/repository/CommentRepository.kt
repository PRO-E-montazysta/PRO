package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Comment
import com.example.e_montazysta.data.model.CommentDAO
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.ICommentRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CommentRepository (private val serviceProvider: IServiceProvider): ICommentRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()

    override suspend fun getListOfComments(): Result<List<Comment>> {
        return try {
            val commentService = serviceProvider.getCommentService()
            val commentDAOs = commentService.getListOfComments(token)
            val comments = commentDAOs.map { it.mapToComment() }
            Result.Success(comments)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCommentDetails(id: Int): Result<Comment> {
        return try {
            val commentService = serviceProvider.getCommentService()
            val commentDAO = commentService.getCommentDetail(token, id)
            val commentDetails = commentDAO.mapToComment()
            Result.Success(commentDetails)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}