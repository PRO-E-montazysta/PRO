package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.event.EventListItem

interface IEventRepository {
    suspend fun getEventDetails(id: Int): Result<Event>

    suspend fun getListOfEvents(payload: Map<String, String>?): Result<List<EventListItem>>
}