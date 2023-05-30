package com.example.e_montazysta.data.model

data class Event(
    val acceptedBy: User,
    val attachments: List<Int>,
    val completionDate: String,
    val createdBy: User,
    val deleted: Boolean,
    val description: String,
    val item: Any,
    val eventDate: String,
    val id: Int,
    val movingDate: String,
    val quantity: Int?,
    val status: String,
    val eventType: EventType
)

enum class EventType {
    TOOL, ELEMENT
}