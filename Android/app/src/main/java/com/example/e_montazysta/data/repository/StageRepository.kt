package com.example.e_montazysta.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.repository.interfaces.IStageRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.stage.StageListItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StageRepository(private val serviceProvider: IServiceProvider) : IStageRepository,
    KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    private val stageService = serviceProvider.getStageService()

    override suspend fun getListOfStages(): Result<List<StageListItem>> {
        return try {
            val stageDAOs = stageService.getListOfStages(token)
            val stages = stageDAOs.map { it.mapToStageListItem() }
            Result.Success(stages)
        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
            Result.Error(e)
        }
    }

    override suspend fun getListOfStages(stages: List<Int>): Result<List<StageListItem>> {
        return try {
            val stageDAOs = stages.map { stageService.getStageDetails(token, it) }
            val stageListItems = stageDAOs.map { it.mapToStageListItem() }
            Result.Success(stageListItems)
        } catch (e: java.lang.Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getStageDetails(id: Int): Result<Stage> {
        return try {
            val stageService = serviceProvider.getStageService()
            val stageDAO = stageService.getStageDetails(token, id)
            val stageDetail = stageDAO.mapToStage()
            Result.Success(stageDetail)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun nextOrderStatus(id: Int): Result<Stage> {
        return try {
            val stageService = serviceProvider.getStageService()
            val stageDAO = stageService.nextOrderStatus(token, id)
            val stageDetail = stageDAO.mapToStage()
            Result.Success(stageDetail)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
