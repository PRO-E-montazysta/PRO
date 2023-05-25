package com.example.e_montazysta.data.model

import java.util.*

data class Stage(
    val id: Int,
    val name: String,
//    val priority: String,
//    val status: String,
//    val plannedStart: Date,
//    val plannedEnd: Date,
//    val startDate: Date?,
//    val plannedDurationTime: Date?,
//    val plannedFittersNumber: User?,
//    val minimumImagesNumber: User?,
    val fitters: List<User?>
//    val comments: List<Comment?>,
//    val toolReleases: List<Release>,
//    val elementReturnReleases: List<Release>,
//    val orderId: Int,
//    val listOfToolsPlannedNumber: List<ToolsType>,
//    val listOfElementsPlannedNumber: List<Element>
) {
}
