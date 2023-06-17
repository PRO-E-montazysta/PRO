package com.example.e_montazysta.ui.stage


import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.data.repository.interfaces.IPlannedItemRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class plannedToolDAO(
    val demandAdHocId: Int?,
    val id: Int,
    val numberOfTools: Int,
    val orderStageId: Int,
    val toolType: ToolType
){
    companion object: KoinComponent{
        val plannedItemRepository: IPlannedItemRepository by inject()
        suspend fun getPlannedTool(id: Int): plannedToolDAO? {
            val result = plannedItemRepository.getPlannedTool(id)
            return when(result){
                is Result.Success -> result.data
                is Result.Error -> null
            }
        }
    }
}

data class plannedElementDAO(
    val demandAdHocId: Int?,
    val element: Element,
    val id: Int,
    val numberOfElements: Int,
    val orderStageId: Int
) {
    companion object {
        suspend fun getPlannedElement(id: Int): plannedElementDAO? {
            val result = plannedToolDAO.plannedItemRepository.getPlannedElement(id)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
        }
    }
}

