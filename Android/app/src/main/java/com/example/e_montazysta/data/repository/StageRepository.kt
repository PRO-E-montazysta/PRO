package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.repository.Interfaces.IStageRepository
import com.example.e_montazysta.data.repository.Interfaces.IUserRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StageRepository (private val serviceProvider: IServiceProvider): IStageRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()

    override suspend fun getListOfStages(): Result<List<Stage>> {
        return try {
            val stageService = serviceProvider.getStageService()
            val stageDAOs = stageService.getListOfStages(token)
            val stages = stageDAOs.map { it.mapToStage() }
            Result.Success(stages)
        } catch (e: Exception) {
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
}