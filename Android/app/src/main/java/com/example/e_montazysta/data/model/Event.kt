package com.example.e_montazysta.data.model

import android.graphics.Color
import java.util.Date

data class Event(
    val acceptedBy: User?,
    val attachments: List<Int>,
    val completionDate: Date?,
    val createdBy: User,
    val deleted: Boolean,
    val description: String?,
    val item: Any,
    val eventDate: Date,
    val id: Int,
    val movingDate: Date?,
    val quantity: Int?,
    val status: EventStatus,
    val eventType: EventType
)

enum class EventType(val type: String) {
    T("Narzędzie"),
    E("Element")
}

enum class EventStatus (val color: Int){
    CREATED(Color.BLUE),         // Blue
    IN_PROGRESS(Color.YELLOW),     // Yellow
    REPAIRED(Color.GREEN),        // Green
    ELIMINATED(Color.RED),      // Red
    MISSING(0x800080),         // Purple
    OTHER(Color.GRAY);           // Gray
}