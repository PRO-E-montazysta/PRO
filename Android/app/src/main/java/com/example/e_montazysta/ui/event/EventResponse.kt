package com.example.e_montazysta.ui.event


import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.EventType
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.model.User
import com.squareup.moshi.JsonClass
import org.koin.core.component.KoinComponent

@JsonClass(generateAdapter = true)
data class ElementEventDAO(
    val acceptedById: Int,
    val attachments: List<Int>,
    val completionDate: String,
    val createdById: Int,
    val deleted: Boolean,
    val description: String,
    val elementId: Int,
    val eventDate: String,
    val id: Int,
    val movingDate: String,
    val quantity: Int,
    val status: String
): KoinComponent {
    suspend fun mapToEvent(): Event{
        val acceptedBy = User.getUserDetails(acceptedById)
        val createdBy =  User.getUserDetails(createdById)
        val element = Element.getElementDetails(elementId)
        return Event(acceptedBy, attachments, completionDate, createdBy, deleted, description, element, eventDate, id, movingDate, quantity, status, EventType.ELEMENT)
    }
}

@JsonClass(generateAdapter = true)
data class ToolEventDAO(
    val acceptedById: Int,
    val attachments: List<Int>,
    val completionDate: String,
    val createdById: Int,
    val deleted: Boolean,
    val description: String,
    val toolId: Int,
    val eventDate: String,
    val id: Int,
    val movingDate: String,
    val status: String
): KoinComponent {
    suspend fun mapToEvent(): Event{
        val acceptedBy = User.getUserDetails(acceptedById)
        val createdBy =  User.getUserDetails(createdById)
        val tool = Tool.getElementDetails(toolId)
        return Event(acceptedBy, attachments, completionDate, createdBy, deleted, description, tool, eventDate, id, movingDate, null, status, EventType.TOOL)
    }
}