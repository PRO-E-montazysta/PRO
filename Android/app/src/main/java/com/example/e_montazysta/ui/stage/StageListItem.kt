package com.example.e_montazysta.ui.stage

import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.helpers.DateUtil

data class StageListItem(
    var id: Int,
    var name: String,
    var plannedStart: String,
    var status: String
)

fun Stage.mapToStageItem(): StageListItem {
    val plannedStartString = plannedStart?.let { DateUtil.format(it) }
    return StageListItem(id, name, if (!plannedStartString.isNullOrBlank()) plannedStartString else "", status)
}