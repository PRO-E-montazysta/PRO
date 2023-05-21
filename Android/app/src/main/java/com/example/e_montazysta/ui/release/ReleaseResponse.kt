package com.example.e_montazysta.ui.release

import com.example.e_montazysta.data.model.Release
import com.squareup.moshi.Json

data class ReleaseDAO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "releaseTime")
    val releaseTime: String,
    @Json(name = "returnTime")
    val returnTime: String?,
    @Json(name = "receivedById")
    val receivedById: String?,
    @Json(name = "releasedById")
    val releasedById: String?,
    @Json(name = "toolId")
    val toolId: String,
    @Json(name = "demandAdHocId")
    val demandAdHocId: String?,
    @Json(name = "orderStageId")
    val orderStageId: String,
){
    fun mapToRelease(): Release {
        return Release(id, releaseTime, returnTime, receivedById, releasedById, toolId, demandAdHocId, orderStageId)
    }
}
