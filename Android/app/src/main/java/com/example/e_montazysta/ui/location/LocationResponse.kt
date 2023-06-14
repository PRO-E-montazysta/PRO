package com.example.e_montazysta.ui.location

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.ILocationRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class LocationDAO(
    val apartmentNumber: String?,
    val city: String,
    val deleted: Boolean,
    val id: Int,
    val orderId: Int,
    val propertyNumber: String,
    val street: String,
    val warehouseId: Int,
    val xcoordinate: Double, //pewnie float/double
    val ycoordinate: Double, //pewnie float/double
    val zipCode: String
){
    companion object : KoinComponent{
        val locationRepository : ILocationRepository by inject()

        suspend fun getLocation(locationId : Int): LocationDAO? {
            return when (val result = locationRepository.getLocation(locationId)) {
                is Result.Success -> result.data
                is Result.Error -> {
                    Log.e(TAG, Log.getStackTraceString(result.exception))
                    null
                }
            }
        }
    }
}