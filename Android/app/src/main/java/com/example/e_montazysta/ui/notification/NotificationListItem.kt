package com.example.e_montazysta.ui.notification

data class NotificationListItem(
    val id: Int,
    val content: String,
    val subContent: String?,
    val createdAt: String,
    val createdById: Int?,
    val elementEventId: Int?,
    val orderStageId: Int?,
    val orderId: Int?,
    val readAt: String?,
    val toolEventId: Int?
)