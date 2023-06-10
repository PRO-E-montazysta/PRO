package com.example.e_montazysta.ui.release

import com.example.e_montazysta.data.model.Release

data class ReleaseListItem(var id: Int, var type: String)

fun Release.mapToReleaseItem(): ReleaseListItem {
    return ReleaseListItem(id, toolId)
}
