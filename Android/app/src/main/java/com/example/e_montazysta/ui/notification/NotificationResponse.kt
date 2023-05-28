package com.example.e_montazysta.ui.notification


import com.example.e_montazysta.data.model.NotificationType
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

data class NotificationDAO(
    val content: String,
    val createdAt: Date,
    val createdById: Int,
    val deleted: Boolean,
    val elementEventId: Int?,
    val id: Int,
    val notificationType: NotificationType,
    val notifiedEmployeeId: Int,
    val orderStageId: Int?,
    val orderId: Int?,
    val readAt: Date?,
    val subContent: String?,
    val toolEventId: Int?
): KoinComponent {
    suspend fun mapToNotification(): Notification{
        val createdBy = getUserDetails(createdById)
        return Notification(content, createdAt, createdBy!!, deleted, elementEventId, id, notificationType, notifiedEmployeeId, orderStageId, orderId, readAt, subContent, toolEventId)
    }

    private suspend fun getUserDetails(userId: Int): User? {
        val userRepository: IUserRepository by inject()
        return when (val result = userRepository.getUserDetails(userId)) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }
}
