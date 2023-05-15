package com.example.e_montazysta.ui.toollist

import com.example.e_montazysta.data.model.Tool

data class ToolListItem(val name: String, val code: String)

fun Tool.mapToToolItem(): ToolListItem {
    return ToolListItem(name, code)
}