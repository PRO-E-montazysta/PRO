package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.ToolType

interface IToolTypeRepository {
    suspend fun getToolType(id: Int): Result<ToolType>

    suspend fun getListOfToolType(): Result<List<ToolType>>
}
