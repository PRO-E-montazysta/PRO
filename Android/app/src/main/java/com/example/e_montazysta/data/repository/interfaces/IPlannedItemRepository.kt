package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.stage.plannedElementDAO
import com.example.e_montazysta.ui.stage.plannedToolDAO

interface IPlannedItemRepository {
    suspend fun getPlannedTool(id: Int): Result<plannedToolDAO>
    suspend fun getPlannedElement(id: Int): Result<plannedElementDAO>
}
