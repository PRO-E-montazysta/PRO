package com.example.e_montazysta.data.model

import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import com.example.e_montazysta.ui.element.ElementInWarehouse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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
) {
    companion object : KoinComponent {
        suspend fun getElementDetails(elementId: Int): Element {
            val elementRepository: IElementRepository by inject()
            val result = elementRepository.getElementDetails(elementId)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}

