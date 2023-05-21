package com.example.e_montazysta.data.model

import com.squareup.moshi.Json

data class ToolDAO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "code")
    val code: String,
    @Json(name = "toolTypeId")
    val toolTypeId: Int,
    @Json(name = "warehouseId")
    val warehouseId: Int
){
    fun mapToTool(): Tool {
        return Tool(id, name, code, toolTypeId, warehouseId)
    }
}