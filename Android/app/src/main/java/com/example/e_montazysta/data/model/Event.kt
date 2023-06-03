package com.example.e_montazysta.data.model

import android.graphics.Color
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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
) {
    companion object : KoinComponent {
        val eventRepository: IEventRepository by inject()
        suspend fun getEventDetails(id: Int): Event {
            val result = eventRepository.getEventDetails(id)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}

enum class EventType(val value: String) {
    T("Narzędzie"),
    E("Element")
}

enum class EventStatus (val color: Int, val value: String){
    CREATED(Color.BLUE, "UTWORZONY"),         // Blue
    IN_PROGRESS(Color.YELLOW, "W REALIZACJI"),     // Yellow
    REPAIRED(Color.GREEN, "NAPRAWIONY"),        // Green
    ELIMINATED(Color.RED, "ZLIKWIDOWANY"),      // Red
    MISSING(0x800080, "ZAGUBIONY"),         // Purple
    OTHER(Color.GRAY, "INNY");           // Gray
}

