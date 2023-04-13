package com.example.e_montazysta.ui.toollist

import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.ui.list.ListItem

class ToolListItem(val name: String, val code: String) : ListItem

fun Tool.mapToToolItem(): ToolListItem {
    return ToolListItem(name, code)
}