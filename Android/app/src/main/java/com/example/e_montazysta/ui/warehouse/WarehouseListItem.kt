package com.example.e_montazysta.ui.warehouse

import com.example.e_montazysta.data.model.Warehouse

data class WarehouseListItem(
    val companyId: Int,
    val deleted: Boolean,
    val description: String,
    val elementInWarehouses: List<Int>,
    val id: Int,
    val locationId: Int?,
    val name: String,
    val openingHours: String,
    val tools: List<Int>
){

}

fun Warehouse.mapToWarehouseItem(): WarehouseListItem {
    return WarehouseListItem(companyId, deleted, description, elementInWarehouses, id, locationId, name, openingHours, tools)
}