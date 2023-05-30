package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepository (private val serviceProvider: IServiceProvider): IEventRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    private val eventService = serviceProvider.getEventService()

    override suspend fun getEventDetails(id: Int): Result<Event> {
        return try {
            val eventDAO = eventService.getToolEventDetails(token, id)
            val eventDetail = eventDAO.mapToEvent()
            Result.Success(eventDetail)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}