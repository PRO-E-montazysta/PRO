package com.example.e_montazysta.ui.toollist

import com.example.e_montazysta.data.model.Tool

data class ToolListItem(
    val id: Int,
    val name: String,
    val code: String,
    val warehouse: String,
    val toolType: String
    )

fun Tool.mapToToolItem(): ToolListItem {
    return ToolListItem(id, name, code, warehouse.name, toolType.name)
}