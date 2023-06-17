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
    val toolReleases: List<Int>,
    val warehouse: Warehouse,
    val toolEvents: List<Int>,
    val toolType: ToolType,
    val status: ToolDAO.toolStatus
) {
    companion object : KoinComponent {
        suspend fun getToolDetails(toolId: Int): Tool {
            val toolRepository: IToolRepository by inject()
            val result = toolRepository.getToolDetails(toolId)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}

