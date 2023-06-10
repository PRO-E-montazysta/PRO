package com.example.e_montazysta.ui.stage

import com.example.e_montazysta.data.model.Stage

data class StageListItem(
    var id: Int,
    var name: String
//    var priority: String,
//    var status: String,
//    var createdAt: String
)

fun Stage.mapToStageItem(): StageListItem {
    return StageListItem(id, name)
}