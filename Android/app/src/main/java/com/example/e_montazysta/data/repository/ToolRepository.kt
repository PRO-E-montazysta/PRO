package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.services.IServiceProvider


class ToolRepository(
    private val serviceProvider: IServiceProvider
) : IToolRepository {
    override suspend fun getTools(): Result<List<Tool>> {
        return try {
            val toolService = serviceProvider.getToolService()
            val toolDAOs = toolService.getTools()
            val tools = toolDAOs.map { it.mapToTool() }
             Result.Success(tools)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}