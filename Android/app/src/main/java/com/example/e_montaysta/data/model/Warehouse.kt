package com.example.e_montaysta.data.model

import com.google.gson.annotations.SerializedName

data class Warehouse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)