package com.example.e_montazysta.ui.warehouse

import com.example.e_montazysta.data.model.Warehouse
import com.example.e_montazysta.ui.location.LocationDAO

data class WarehouseDAO(
    val companyId: Int,
    val deleted: Boolean,
    val description: String,
    val elementInWarehouses: List<Int>,
    val id: Int,
    val locationId: Int?,
    val name: String,
    val openingHours: String,
    val tools: List<Int>
) {
    suspend fun mapToWarehouse(): Warehouse {
        val location = locationId?.let { LocationDAO.getLocation(locationId) }
        return Warehouse(
            companyId,
            deleted,
            description,
            elementInWarehouses,
            id,
            location,
            name,
            openingHours,
            tools
        )
    }
}

data class WarehouseFilterDAO(
    val id: Int,
    val description: String,
    val name: String,
    val openingHours: String?,
    val fullAddress: String?
)