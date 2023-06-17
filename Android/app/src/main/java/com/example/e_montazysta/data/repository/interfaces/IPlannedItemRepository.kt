package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.stage.PlannedElementDAO
import com.example.e_montazysta.ui.stage.PlannedToolDAO

interface IPlannedItemRepository {
    suspend fun getPlannedTool(id: Int): Result<PlannedToolDAO>
    suspend fun getPlannedElement(id: Int): Result<PlannedElementDAO>
}
