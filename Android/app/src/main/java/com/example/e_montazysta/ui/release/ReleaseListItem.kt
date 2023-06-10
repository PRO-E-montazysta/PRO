package com.example.e_montazysta.ui.release

import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.helpers.DateUtil

data class ReleaseListItem(
    val id: Int,
    val releaseTime: String?,
    val returnTime: String?,
    val releasedBy: User?,
    val itemName: String,
    val releasedQuantity: Int?,
    val returnedQuantity: Int?,
    val isElement: Boolean
    )

suspend fun Release.mapToReleaseItem(): ReleaseListItem {
    val releaseTimeString = releaseTime?.let { DateUtil.format(it) }
    val returnTimeString = returnTime?.let { DateUtil.format(it) }
    return ReleaseListItem(id, releaseTimeString, returnTimeString, releasedBy, "", 0, 0, true)
}
