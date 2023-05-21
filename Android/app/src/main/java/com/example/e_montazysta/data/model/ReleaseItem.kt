package com.example.e_montazysta.data.model

data class ReleaseItem(
    val isElement: Boolean = false,
    val id: Int,
    val name: String,
    val code: String,
    val toolType: Int,
    val quantity: Int,
    val warehouse: Int){

    fun getQuantityInWarehouse(id: Int): Int {
        return 99
    }
}
fun mapToReleaseItem(item: Any): ReleaseItem {
    return when(item){
        is Element -> ReleaseItem(true, item.id, item.name, item.code, 0, 0, 0)
        is Tool -> ReleaseItem(false, item.id, item.name, item.code, item.toolType, 0, item.warehouse)
        else -> throw IllegalArgumentException("asd")
    }
}

fun Element.mapToReleaseItem(): ReleaseItem {
    return ReleaseItem(true, id, name, code, 0, 0, 0)
}

fun Tool.mapToReleaseItem(): ReleaseItem {
    return ReleaseItem(false, id, name, code, toolType, 1, warehouse)
}