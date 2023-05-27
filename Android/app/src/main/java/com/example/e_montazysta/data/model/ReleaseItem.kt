package com.example.e_montazysta.data.model

import com.example.e_montazysta.ui.release.ElementReleaseRequest
import com.example.e_montazysta.ui.release.ToolReleaseRequest

data class ReleaseItem(
    val isElement: Boolean = false,
    val id: Int,
    val name: String,
    val code: String,
    val toolType: String,
    var quantity: Int = 1,
    val warehouse: String){

    fun getQuantityInWarehouse(id: Int): Int {
        return 99
    }
    fun mapToElementReleaseRequest(): ElementReleaseRequest {
        return ElementReleaseRequest(code, quantity)
    }
    fun mapToToolReleaseRequest(): ToolReleaseRequest {
        return ToolReleaseRequest(code)
    }
}

fun mapToReleaseItem(item: Any): ReleaseItem {
    return when(item){
        is Element -> ReleaseItem(true, item.id, item.name, item.code, "", 1, "")
        is Tool -> ReleaseItem(false, item.id, item.name, item.code, item.toolType, 1, item.warehouse)
        else -> throw IllegalArgumentException("asd")
    }
}

fun Element.mapToReleaseItem(): ReleaseItem {
    return ReleaseItem(true, id, name, code, "", 0, "")
}

fun Tool.mapToReleaseItem(): ReleaseItem {
    return ReleaseItem(false, id, name, code, toolType, 1, warehouse)
}
