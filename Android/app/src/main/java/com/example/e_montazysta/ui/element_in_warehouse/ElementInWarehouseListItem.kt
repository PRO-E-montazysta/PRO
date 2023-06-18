package com.example.e_montazysta.ui.element_in_warehouse

data class ElementInWarehouseListItem(
    val deleted: Boolean,
    val element: String,
    val id: Int,
    val inWarehouseCount: Int,
    val rack: String,
    val shelf: String,
    val warehouse: String
){
    fun getListItemInfo(): String {
        return "Nazwa: " + element + "\nIlość: " + inWarehouseCount.toString() +
                "\nRegał: " + rack +
                "\nPółka: " + shelf
    }
}