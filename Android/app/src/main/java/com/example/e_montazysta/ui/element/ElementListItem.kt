package com.example.e_montazysta.ui.element

data class ElementListItem(
    val attachmentId: Any,
    val code: String,
    val deleted: Boolean,
    val elementEvents: List<Int>,
    val elementInWarehouses: List<ElementInWarehouse>,
    val elementReturnReleases: List<Int>,
    val id: Int,
    val listOfElementsPlannedNumber: List<Int>,
    val name: String,
    val quantityInUnit: Int,
    val typeOfUnit: String
){
    fun getQuantityInWarehouse(warehouseId: Int): String{
        val warehouse = elementInWarehouses.first { it.warehouseId ==  warehouseId }
        return warehouse.inWarehouseCount.toString()
    }
}