package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage

open interface IStageRepository {
    suspend fun getListOfStages() : Result<List<Stage>>
    suspend fun getStageDetails(id: Int): Result<Stage>
}
