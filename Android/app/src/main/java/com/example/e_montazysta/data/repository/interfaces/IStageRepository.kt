package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.ui.stage.StageListItem

open interface IStageRepository {
    suspend fun getListOfStages(): Result<List<StageListItem>>
    suspend fun getListOfStages(stages: List<Int>): Result<List<StageListItem>>
    suspend fun getStageDetails(id: Int): Result<Stage>
}
