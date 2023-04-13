package com.example.e_montazysta.data.model

import com.squareup.moshi.Json
import com.example.e_montazysta.data.model.Tool

data class ToolDAO(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "code")
    val code: String = "",
    @Json(name = "tool_type")
    val toolType: ToolType,
    @Json(name = "warehouse")
    val warehouse: Warehouse
){
    fun mapToTool(): Tool {
        return Tool(id, name, code, toolType, warehouse)
    }
}