package com.example.e_montazysta.ui.event


import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.EventStatus
import com.example.e_montazysta.data.model.EventType
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.helpers.DateUtil
import com.squareup.moshi.JsonClass
import org.koin.core.component.KoinComponent
import java.util.Date

@JsonClass(generateAdapter = true)
data class ElementEventDAO(
    val acceptedById: Int?,
    val attachments: List<Int>,
    val completionDate: Date?,
    val createdById: Int,
    val deleted: Boolean,
    val description: String?,
    val elementId: Int,
    val eventDate: Date,
    val id: Int,
    val movingDate: Date?,
    val quantity: Int,
    val status: EventStatus,
): KoinComponent {
    suspend fun mapToEvent(): Event{
        var acceptedBy: User?
        if (acceptedById != null) {
            acceptedBy = User.getUserDetails(acceptedById)
            } else {
            acceptedBy = null
        }

        val createdBy =  User.getUserDetails(createdById)
        val element = Element.getElementDetails(elementId)
        return Event(acceptedBy, attachments, completionDate, createdBy, deleted, description, element, eventDate, id, movingDate, quantity, status, EventType.E)
    }
}

@JsonClass(generateAdapter = true)
data class ToolEventDAO(
    val acceptedById: Int?,
    val attachments: List<Int>,
    val completionDate: Date?,
    val createdById: Int,
    val deleted: Boolean,
    val description: String?,
    val toolId: Int,
    val eventDate: Date,
    val id: Int,
    val movingDate: Date?,
    val status: EventStatus,
) {
    suspend fun mapToEvent(): Event{
        var acceptedBy: User?
        if (acceptedById != null) {
            acceptedBy = User.getUserDetails(acceptedById)
        } else {
            acceptedBy = null
        }

        val createdBy =  User.getUserDetails(createdById)
        val tool = Tool.getElementDetails(toolId)
        return Event(acceptedBy, attachments, completionDate, createdBy, deleted, description, tool, eventDate, id, movingDate, null, status, EventType.T)
    }
}

@JsonClass(generateAdapter = true)
data class FilterEventDAO(
    val acceptedById: Int?,
    val completionDate: Date?,
    val createdById: Int,
    val description: String?,
    val eventDate: Date,
    val eventType: EventType,
    val id: Int,
    val itemName: String,
    val movingDate: Date?,
    val status: EventStatus
    ){
    fun mapToEventListItem(): EventListItem {
        return EventListItem(id, itemName, eventType, status, DateUtil.format(eventDate))
    }
}