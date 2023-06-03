package com.example.e_montazysta.data.model

import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

/**
 * Data class that captures tool information
 */
data class Tool(
    val id: Int,
    val name: String,
    val code: String,
    val createdAt: Date,
    val toolReleases: List<Release>,
    val warehouse: Warehouse,
    val toolEvents: List<Event>,
    val toolType: ToolType,
    val status: ToolDAO.toolStatus
) {
    companion object : KoinComponent{
        suspend fun getElementDetails(toolId: Int): Any {
            val toolRepository: IToolRepository by inject()
            val result = toolRepository.getToolDetails(toolId)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}

