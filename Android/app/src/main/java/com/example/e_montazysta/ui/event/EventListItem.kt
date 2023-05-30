package com.example.e_montazysta.ui.event

import com.example.e_montazysta.data.model.Event

data class EventListItem(
    var id: Int,
    var name: String
//    var priority: String,
//    var status: String,
//    var createdAt: String
)

fun Event.mapToEventItem(): EventListItem {
    return EventListItem(id, name)
}