package com.example.e_montazysta.data.model

import com.squareup.moshi.Json
import org.koin.core.component.KoinComponent

data class User (
    @Json(name = "id")
    val id: Int,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String
): KoinComponent {
    override fun toString(): String = "$firstName $lastName"
}
