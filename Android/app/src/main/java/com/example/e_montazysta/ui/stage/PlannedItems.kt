package com.example.e_montazysta.ui.stage


import com.example.e_montazysta.data.model.ElementDAO
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.data.repository.interfaces.IPlannedItemRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class PlannedToolDAO(
    val demandAdHocId: Int?,
    val id: Int,
    val numberOfTools: Int,
    val orderStageId: Int,
    val toolType: ToolType
){
    companion object: KoinComponent{
        val plannedItemRepository: IPlannedItemRepository by inject()
        suspend fun getPlannedTool(id: Int): PlannedToolDAO? {
            val result = plannedItemRepository.getPlannedTool(id)
            return when(result){
                is Result.Success -> result.data
                is Result.Error -> null
            }
        }
    }
    fun getListItemInfo(): String {
        return "Ilość: $numberOfTools"
    }
}

data class PlannedElementDAO(
    val demandAdHocId: Int?,
    val element: ElementDAO,
    val id: Int,
    val numberOfElements: Int,
    val orderStageId: Int
) {
    companion object {
        suspend fun getPlannedElement(id: Int): PlannedElementDAO? {
            val result = PlannedToolDAO.plannedItemRepository.getPlannedElement(id)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
        }
    }
    fun getListItemInfo(): String {
        return "Ilość: $numberOfElements"
    }
}

