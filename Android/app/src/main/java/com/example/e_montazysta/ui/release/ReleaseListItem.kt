package com.example.e_montazysta.ui.release

import com.example.e_montazysta.data.model.Release

data class ReleaseListItem(val id: String, val type: String)

fun Release.mapToReleaseItem(): ReleaseListItem {
    return ReleaseListItem(id, toolId)
}