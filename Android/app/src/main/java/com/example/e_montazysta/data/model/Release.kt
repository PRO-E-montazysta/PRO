package com.example.e_montazysta.data.model

import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

data class Release(
    val id: Int,
    val releaseTime: Date?,
    val returnTime: Date?,
    val receivedBy: User?,
    val releasedBy: User?,
    val tool: Tool?,
    val element: Element?,
    val demandAdHocId: Int?,
    val orderStageId: Int,
    val releasedQuantity: Int?,
    val returnedQuantity: Int?,
    val isElement: Boolean
) {
    companion object : KoinComponent {
        val releaseRepository: IReleaseRepository by inject()
        suspend fun getReleaseDetails(id: Int): Release {
            val result = releaseRepository.getReleaseDetail(id)
            return when (result) {
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}
