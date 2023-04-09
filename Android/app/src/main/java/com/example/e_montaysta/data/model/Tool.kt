package com.example.e_montaysta.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * Data class that captures tool information
 */
data class Tool(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("tool_type")
    val toolType: ToolType,
    @SerializedName("warehouse")
    val warehouse: Warehouse
)