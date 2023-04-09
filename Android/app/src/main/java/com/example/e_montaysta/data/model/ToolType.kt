package com.example.e_montaysta.data.model

import com.google.gson.annotations.SerializedName

data class ToolType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)