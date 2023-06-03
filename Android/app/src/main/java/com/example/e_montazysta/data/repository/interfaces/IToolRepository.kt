package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.ui.toollist.ToolListItem

interface IToolRepository {
    suspend fun getFilterTools() : Result<List<ToolListItem>>
    suspend fun getToolByCode(code: String?) : Result<Tool>
    suspend fun getToolDetails(toolId: Int): Result<Tool>
}