package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.location.LocationDAO

interface ILocationRepository {
    suspend fun getLocation(id: Int): Result<LocationDAO>
}
