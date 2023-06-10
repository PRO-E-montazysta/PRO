package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.toollist.ToolListItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ToolRepository(
    private val serviceProvider: IServiceProvider
) : IToolRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    private val toolService = serviceProvider.getToolService()

    override suspend fun getFilterTools(payload: Map<String, String>?): Result<List<ToolListItem>> {
        return try {
            val toolService = serviceProvider.getToolService()
            val toolItemDAOs: List<ToolListItem>
            if (payload.isNullOrEmpty()) {
                toolItemDAOs = toolService.getFilterTools(token)
            } else{
                toolItemDAOs = toolService.getFilterTools(token, payload)
            }
            Result.Success(toolItemDAOs)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getToolByCode(code: String?): Result<Tool> {
        return try {
            val toolService = serviceProvider.getToolService()
            val toolDAO = toolService.getToolByCode(token, code)
            Result.Success(toolDAO.mapToTool())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getToolDetails(id: Int): Result<Tool> {
        return try {
            val toolDAO = toolService.getToolDetails(token, id)
            Result.Success(toolDAO.mapToTool())
        } catch (e: java.lang.Exception) {
            Result.Error(e)
        }
    }
}
