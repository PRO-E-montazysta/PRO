package com.example.e_montazysta.data.model

import android.content.ContentValues.TAG
import android.util.Log
import com.example.e_montazysta.data.repository.interfaces.IToolTypeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class ToolType(
    val id: Int,
    val name: String,
    val inServiceCount: Int?,
    val criticalNumber: Int?,
    val availableCount: Int?,
    val attachments: List<Int?>,
    val tools: List<Int?>,
    val companyId: Int?,
    val listOfToolsPlannedNumber: List<Int?>
){
    companion object: KoinComponent{
        suspend fun getToolType(toolTypeId: Int): ToolType {
            val toolTypeRepository: IToolTypeRepository by inject()
            val result = toolTypeRepository.getToolType(toolTypeId)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> {
                    Log.e(TAG, Log.getStackTraceString(result.exception))
                    throw result.exception
                }
            }
        }
    }
}