package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Event
import com.example.e_montazysta.data.model.Result

interface IEventRepository {
    suspend fun getEventDetails(id: Int): Result<Event>
}