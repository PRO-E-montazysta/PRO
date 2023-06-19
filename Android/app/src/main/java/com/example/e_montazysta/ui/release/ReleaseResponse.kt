package com.example.e_montazysta.ui.release

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.helpers.DateUtil
import java.util.Date

data class ReleaseToolDAO(
    val id: Int,
    val releaseTime: Date,
    val returnTime: Date?,
    val receivedById: Int?,
    val releasedById: Int?,
    val toolId: Int,
    val demandAdHocId: Int?,
    val orderStageId: Int
) {
    suspend fun mapToRelease(): Release {
        val releasedBy = if (releasedById != null) User.getUserDetails(releasedById) else null
        val receivedBy = if (receivedById != null) User.getUserDetails(receivedById) else null
        val tool = Tool.getToolDetails(toolId)
        return Release(
            id,
            releaseTime,
            returnTime,
            receivedBy,
            releasedBy,
            tool,
            null,
            demandAdHocId,
            orderStageId,
            null,
            null,
            false
        )
    }

    suspend fun maptoReleaseListItem(): ReleaseListItem {
        val releasedBy = if (releasedById != null) User.getUserDetails(releasedById) else null
        val itemName = Tool.getToolDetails(toolId).name
        val releaseTimeString = DateUtil.format(releaseTime)
        val returnTimeString = returnTime?.let { DateUtil.format(it) }
        return ReleaseListItem(
            id,
            releaseTimeString,
            returnTimeString,
            releasedBy,
            itemName,
            null,
            null,
            false
        )
    }
}

data class ReleaseElementDAO(
    val id: Int,
    val releaseTime: Date,
    val returnTime: Date?,
    val releasedQuantity: Int,
    val returnedQuantity: Int,
    val receivedById: Int?,
    val releasedById: Int?,
    val elementId: Int,
    val demandAdHocId: Int?,
    val orderStageId: Int
) {
    suspend fun mapToRelease(): Release {
        val releasedBy = if (releasedById != null) User.getUserDetails(releasedById) else null
        val receivedBy = if (receivedById != null) User.getUserDetails(receivedById) else null
        val element = Element.getElementDetails(elementId)
        return Release(
            id,
            releaseTime,
            returnTime,
            receivedBy,
            releasedBy,
            null,
            element,
            demandAdHocId,
            orderStageId,
            releasedQuantity,
            returnedQuantity,
            true
        )

    }

    suspend fun maptoReleaseListItem(): ReleaseListItem {
        val releasedBy = if (releasedById != null) User.getUserDetails(releasedById) else null
        val itemName = Element.getElementDetails(elementId).name
        val releaseTimeString = DateUtil.format(releaseTime)
        val returnTimeString = returnTime?.let { DateUtil.format(it) }

        return ReleaseListItem(
            id,
            releaseTimeString,
            returnTimeString,
            releasedBy,
            itemName,
            releasedQuantity,
            returnedQuantity,
            true
        )
    }
}
