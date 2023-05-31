package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool

interface IToolRepository {
    suspend fun getTools() : Result<List<Tool>>
    suspend fun getToolByCode(code: String?) : Result<Tool>
    suspend fun getToolDetails(toolId: Int): Result<Tool>
}