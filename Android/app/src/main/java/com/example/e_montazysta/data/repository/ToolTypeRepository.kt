package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.data.repository.interfaces.IToolTypeRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToolTypeRepository(private val serviceProvider: IServiceProvider) : IToolTypeRepository,
    KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    val toolTypeService = serviceProvider.getToolTypeService()

    override suspend fun getToolType(id: Int): Result<ToolType> {
        return try {
            val result = toolTypeService.getToolType(token, id)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getListOfToolType(): Result<List<ToolType>> {
        return try {
            val result = toolTypeService.getListOFToolType(token)
            Result.Success(result)
        } catch (e: java.lang.Exception) {
            Result.Error(e)
        }
    }
}