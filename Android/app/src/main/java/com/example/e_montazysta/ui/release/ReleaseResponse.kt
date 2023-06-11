package com.example.e_montazysta.ui.release

import com.example.e_montazysta.data.model.Release

data class ReleaseDAO(
    val id: Int,
    val releaseTime: String,
    val returnTime: String?,
    val releasedQuantity: Int?,
    val returnedQuantity: Int?,
    val receivedById: String?,
    val releasedById: String?,
    val toolId: String?,
    val elementId: String?,
    val demandAdHocId: String?,
    val orderStageId: String,
){
    fun mapToRelease(): Release {
        return Release(id, releaseTime, returnTime, receivedById, releasedById, toolId, demandAdHocId, orderStageId)
    }
}
