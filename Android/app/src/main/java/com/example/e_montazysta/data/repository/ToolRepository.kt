package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ToolRepository(
    private val serviceProvider: IServiceProvider
) : IToolRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    override suspend fun getTools(): Result<List<Tool>> {
        return try {
            val toolService = serviceProvider.getToolService()
            val toolDAOs = toolService.getTools(token)
            val tools = toolDAOs.map { it.mapToTool() }
            Result.Success(tools)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}