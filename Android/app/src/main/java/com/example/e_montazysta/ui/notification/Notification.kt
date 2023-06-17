package com.example.e_montazysta.ui.notification


import com.example.e_montazysta.data.model.NotificationType
import com.example.e_montazysta.data.model.User
import java.util.Date

data class Notification(
    val content: String,
    val createdAt: Date,
    val createdBy: User,
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
) {
    fun mapToNotificationListItem(): NotificationListItem {
        return NotificationListItem(
            id,
            content,
            subContent,
            createdAt.toString(),
            createdBy.id,
            elementEventId,
            orderStageId,
            orderId,
            readAt.toString(),
            toolEventId
        )
    }
}