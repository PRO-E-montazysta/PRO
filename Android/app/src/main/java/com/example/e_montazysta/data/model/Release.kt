package com.example.e_montazysta.data.model

import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class Release(
    val id: Int,
    val releaseTime: String?,
    val returnTime: String?,
    val receivedById: String?,
    val releasedById: String?,
    val toolId: String,
    val demandAdHocId: String?,
    val orderStageId: String,
){
    companion object : KoinComponent {
        val releaseRepository: IReleaseRepository by inject()
        suspend fun getEventDetails(id: Int): Release {
            val result = releaseRepository.getReleaseDetail(id)
            return when(result){
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}
