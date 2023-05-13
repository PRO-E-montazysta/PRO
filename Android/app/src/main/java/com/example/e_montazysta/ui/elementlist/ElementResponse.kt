package com.example.e_montazysta.data.model

import com.squareup.moshi.Json

data class ElementDAO(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "code")
    val code: String = "",
    @Json(name = "unit")
    val unit: String = "",
    @Json(name = "qty")
    val qty: Int = 0,
    @Json(name = "warehouse")
    val warehouse: String = ""
){
    fun mapToElement(): Element {
        return Element(id, name, code, unit, qty, warehouse)
    }
}