package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.data.repository.interfaces.ILocationRepository
import com.example.e_montazysta.data.repository.interfaces.IToolTypeRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.location.LocationDAO
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocationRepository (private val serviceProvider: IServiceProvider): ILocationRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    val locationService = serviceProvider.getLocationService()

    override suspend fun getLocation(id: Int): Result<LocationDAO> {
        return try {
            val result = locationService.getToolType(token, id)
            Result.Success(result)
        } catch (e: Exception){
            Result.Error(e)
        }
    }
}