package com.example.e_montazysta.data.model

import java.math.BigDecimal
import com.example.e_montazysta.ui.stage.StageStatus
import java.util.Date

data class Stage(
    val id: Int,
    val name: String,
    val status: StageStatus,
    val price: BigDecimal,
    val plannedStart: Date?,
    val plannedEnd: Date?,
    val startDate: Date?,
    val endDate: Date?,
    val plannedDurationTime: Date?,
    val plannedFittersNumber: Int,
    val minimumImagesNumber: Int,
    val fitters: List<User?>,
    val comments: List<Comment?>,
    val toolReleases: List<Release?>,
    val elementReturnReleases: List<Release>?,
    val orderId: Int,
    val listOfToolsPlannedNumber: List<Int>,
    val listOfElementsPlannedNumber: List<Int>
)
