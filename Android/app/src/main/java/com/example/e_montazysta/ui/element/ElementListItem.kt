package com.example.e_montazysta.ui.element

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.TypeOfUnit

data class ElementListItem(
    val attachmentId: Any,
    val code: String,
    val deleted: Boolean,
    val elementEvents: List<Int>,
    val elementInWarehouses: List<ElementInWarehouse>?,
    val elementReturnReleases: List<Int>,
    val id: Int,
    val listOfElementsPlannedNumber: List<Int>,
    val name: String,
    val quantityInUnit: Double,
    val typeOfUnit: TypeOfUnit
) {
    fun getQuantityInWarehouse(warehouseId: Int): String {
        val warehouse = elementInWarehouses?.first { it.warehouseId == warehouseId }
        return warehouse?.inWarehouseCount.toString()
    }
}

fun Element.mapToElementItem(): ElementListItem {
    return ElementListItem(
        name,
        code,
        deleted,
        elementEvents,
        elementInWarehouses,
        elementReturnReleases,
        id,
        listOfElementsPlannedNumber,
        name,
        quantityInUnit,
        typeOfUnit
    )
}