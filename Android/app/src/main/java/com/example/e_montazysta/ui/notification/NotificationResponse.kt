package com.example.e_montazysta.ui.notification


import com.example.e_montazysta.data.model.NotificationType
import java.time.LocalDateTime

data class Notification(
    val content: String,
    val createdAt: LocalDateTime,
    val createdById: Int,
    val deleted: Boolean,
    val elementEventId: Int?,
    val id: Int,
    val notificationType: NotificationType,
    val notifiedEmployeeId: Int,
    val notificationStageId: Int?,
    val readAt: LocalDateTime?,
    val subContent: String?,
    val toolEventId: Int?
){

}