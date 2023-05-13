package com.example.e_montazysta.data.model

import com.squareup.moshi.Json

data class ToolType(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)