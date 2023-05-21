package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Comment
import com.example.e_montazysta.data.model.Result
open interface ICommentRepository {
    suspend fun getListOfComments() : Result<List<Comment>>
    suspend fun getCommentDetails(id: Int): Result<Comment>
}
