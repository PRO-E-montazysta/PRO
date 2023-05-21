package com.example.e_montazysta.data.model

import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.squareup.moshi.JsonClass
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

@JsonClass(generateAdapter = true)
data class CommentDAO(
    val attachments: List<Int>,
    val content: String,
    val createdAt: Date,
    val deleted: Boolean,
    val id: Int,
    val messageCreatorId: Int,
    val orderStageId: Int
) : KoinComponent {
    val userRepository: IUserRepository by inject()
    suspend fun mapToComment(): Comment {
        val messageCreator = getUserDetails(messageCreatorId)
        return Comment(attachments, content, createdAt, deleted, id, messageCreator, orderStageId)
    }

    private suspend fun getUserDetails(userId: Int?): User? {
        if (userId == null) {
            return null
        }
        return when (val result = userRepository.getUserDetails(userId)) {
            is Result.Success -> result.data
            is Result.Error -> null

        }
    }
}