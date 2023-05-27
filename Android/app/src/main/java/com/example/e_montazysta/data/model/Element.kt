package com.example.e_montazysta.data.model

import com.example.e_montazysta.ui.element.ElementInWarehouse

data class Element(
    val attachmentId: String?,
    val code: String,
    val deleted: Boolean,
    val elementEvents: List<Int>,
    val elementInWarehouses: List<ElementInWarehouse>?,
    val elementReturnReleases: List<Int>,
    val id: Int,
    val listOfElementsPlannedNumber: List<Int>,
    val name: String,
    val quantityInUnit: Double,
    val typeOfUnit: String
    )

