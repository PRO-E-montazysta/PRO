package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.EventType
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.event.EventListItem
import com.example.e_montazysta.ui.event.FilterEventDAO
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepository(private val serviceProvider: IServiceProvider) : IEventRepository,
    KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    private val eventService = serviceProvider.getEventService()

    override suspend fun getEventDetails(id: Int, type: EventType): Result<Event> {
        return try {
            when(type){
                EventType.E -> {
                    val eventDAO = eventService.getElementEventDetails(token, id)
                    val eventDetail = eventDAO.mapToEvent()
                    Result.Success(eventDetail)
                }
                EventType.T -> {
                    val eventDAO = eventService.getToolEventDetails(token, id)
                    val eventDetail = eventDAO.mapToEvent()
                    Result.Success(eventDetail)
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getListOfEvents(payload: Map<String, String>?): Result<List<EventListItem>> {
        return try {
            var eventDAOs: List<FilterEventDAO>
            if (payload.isNullOrEmpty()) {
                eventDAOs = eventService.getFilteredEvents(token)
            } else {
                eventDAOs = eventService.getFilteredEvents(token, payload)
            }
            val events = eventDAOs.map { it.mapToEventListItem() }
            Result.Success(events)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}