package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IPlannedItemRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.stage.plannedElementDAO
import com.example.e_montazysta.ui.stage.plannedToolDAO
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlannedItemRepository(private val serviceProvider: IServiceProvider) : IPlannedItemRepository,
    KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    val itemService = serviceProvider.getPlannedItemService()

    override suspend fun getPlannedTool(id: Int): Result<plannedToolDAO> {
        return try {
            val plannedToolDAO = itemService.getPlannedTool(token, id)
            Result.Success(plannedToolDAO)
        } catch (e: java.lang.Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPlannedElement(id: Int): Result<plannedElementDAO> {
        return try {
            val plannedElementDAO = itemService.getPlannedElement(token, id)
            Result.Success(plannedElementDAO)
        } catch (e: java.lang.Exception) {
            Result.Error(e)
        }
    }
}