package com.example.e_montazysta.ui.warehouse

data class Warehouse(
    val companyId: Int,
    val deleted: Boolean,
    val description: String,
    val elementInWarehouses: List<Int>,
    val id: Int,
    val locationId: Int,
    val name: String,
    val openingHours: String,
    val tools: List<Int>
)