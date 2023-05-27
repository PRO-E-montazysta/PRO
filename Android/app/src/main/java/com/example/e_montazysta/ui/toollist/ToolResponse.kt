package com.example.e_montazysta.data.model

import com.squareup.moshi.Json

data class ToolDAO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "code")
    val code: String,
    @Json(name = "toolType")
    val toolType: String,
    @Json(name = "warehouse")
    val warehouse: String
){
    fun mapToTool(): Tool {
        return Tool(id, name, code, toolType, warehouse)
    }
}
