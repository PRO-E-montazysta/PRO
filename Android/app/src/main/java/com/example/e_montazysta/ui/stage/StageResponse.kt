package com.example.e_montazysta.ui.stage

import com.example.e_montazysta.data.model.*
import com.squareup.moshi.Json

data class StageDAO (
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
//    @Json(name = "priority")
//    val priority: String,
//    @Json(name = "status")
//    val status: String,
//    @Json(name = "plannedStart")
//    val plannedStart: Date,
//    @Json(name = "plannedEnd")
//    val plannedEnd: Date,
//    @Json(name = "startDate")
//    val startDate: Date?,
//    @Json(name = "plannedDurationTime")
//    val plannedDurationTime: Date?,
//    @Json(name = "plannedFittersNumber")
//    val plannedFittersNumber: Int,
//    @Json(name = "minimumImagesNumber")
//    val minimumImagesNumber: Int,
    @Json(name = "fitters")
    val fitters: List<User>
//    @Json(name = "comments")
//    val comments: List<Int>,
//    @Json(name = "toolReleases")
//    val toolReleases: List<Release>,
//    @Json(name = "elementReturnReleases")
//    val elementReturnReleases: List<Release>,
//    @Json(name = "orderId")
//    val orderId: Int,
//    @Json(name = "listOfToolsPlannedNumber")
//    val listOfToolsPlannedNumber: List<ToolsType>,
//    @Json(name = "listOfElementsPlannedNumber")
//    val listOfElementsPlannedNumber: List<Element>
)  {
    suspend fun mapToStage(): Stage {
        return Stage(id, name, fitters)
    }
}