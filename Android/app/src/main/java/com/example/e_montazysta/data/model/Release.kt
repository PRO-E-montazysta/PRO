package com.example.e_montazysta.data.model

data class Release(
    val id: String,
    val releaseTime: String?,
    val returnTime: String?,
    val receivedById: String?,
    val releasedById: String?,
    val toolId: String,
    val demandAdHocId: String?,
    val orderStageId: String,
) {
}
