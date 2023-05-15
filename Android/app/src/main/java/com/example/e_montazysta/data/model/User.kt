package com.example.e_montazysta.data.model

import com.squareup.moshi.Json

data class User (
    @Json(name = "id")
    val id: Int,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String
){
    override fun toString(): String = "$firstName $lastName"
}