package com.example.e_montazysta.ui.stage

import com.example.e_montazysta.helpers.DateUtil
import java.util.Date

data class StageListItem(
    val id: Int,
    val name: String,
    val orderName: String,
    val status: StageStatus,
    val plannedStart: Date?,
    val plannedEnd: Date?
//    var createdAt: String
) {
    fun getListItemInfo(): String {
        return "Status: " + status.value +
                (if (plannedStart != null && plannedEnd != null) "\nPlanowana data: "
                        + DateUtil.format(plannedStart) + " - "
                        + DateUtil.format(plannedEnd.time) else "")
    }
}
