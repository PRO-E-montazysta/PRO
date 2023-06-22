package com.example.e_montazysta.ui.event

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.EventStatus
import com.example.e_montazysta.data.model.EventType
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.helpers.DateUtil

data class EventListItem(
    val id: Int,
    val itemName: String,
    val eventType: EventType,
    val status: EventStatus,
    val eventDate: String
)

fun Event.mapToEventListItem(): EventListItem {
    val name = when (item) {
        is Element -> item.name
        is Tool -> item.name
        else -> throw IllegalArgumentException("Coś poszło nie tak...")
    }
    return EventListItem(id, name, eventType, status, DateUtil.format(eventDate))
}