package com.example.e_montazysta.ui.element

data class ElementInWarehouse(
    val deleted: Boolean,
    val elementId: Int,
    val id: Int,
    val inUnitCount: Int,
    val inWarehouseCount: Int,
    val rack: String,
    val shelf: String,
    val warehouseId: Int
)