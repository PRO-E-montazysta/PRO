package com.example.e_montazysta.data.model

import java.util.*

data class Stage(
    val id: Int,
    val name: String,
    val status: String,
    val price: Float,
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
    val elementReturnReleases: List<Int>,
    val orderId: Int,
    val listOfToolsPlannedNumber: List<Int>,
    val listOfElementsPlannedNumber: List<Int>
)
