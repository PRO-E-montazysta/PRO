package com.example.e_montazysta.data.model

data class ElementDAO(
    val attachmentId: String?,
    val code: String,
    val deleted: Boolean,
    val elementEvents: List<Int>,
    val elementInWarehouses: List<Int>,
    val elementReturnReleases: List<Int>,
    val id: Int,
    val listOfElementsPlannedNumber: List<Int>,
    val name: String,
    val quantityInUnit: Double,
    val typeOfUnit: String
) {
    fun mapToElement(): Element {
        return Element(
            attachmentId,
            code,
            false,
            elementEvents,
            null,
            elementReturnReleases,
            id,
            listOfElementsPlannedNumber,
            name,
            quantityInUnit,
            typeOfUnit
        )
    }
}