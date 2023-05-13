package com.example.e_montazysta.ui.elementlist

import com.example.e_montazysta.data.model.Tool

data class ElementListItem(val name: String, val code: String)

fun Tool.mapToToolItem(): ElementListItem {
    return ElementListItem(name, code)
}