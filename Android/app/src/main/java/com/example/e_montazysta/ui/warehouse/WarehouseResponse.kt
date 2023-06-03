package com.example.e_montazysta.ui.warehouse

import com.example.e_montazysta.data.model.Warehouse

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
    fun mapToWarehouse(): Warehouse {
        return Warehouse(companyId, deleted, description, elementInWarehouses, id, locationId, name, openingHours, tools)
    }
}

data class WarehouseFilterDAO(
    val description: String,
    val name: String,
    val zipCode: String,
)