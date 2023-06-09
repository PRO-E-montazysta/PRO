package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.stage.StageDAO

interface IReleaseRepository {
    suspend fun getRelease() : Result<List<Release>>

    suspend fun getReleaseDetail(id: Int): Result<Release>
    suspend fun createRelease(items: List<ReleaseItem>, stageId: Int): Result<List<StageDAO>>
}
