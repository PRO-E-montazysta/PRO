package com.example.e_montazysta.data.model

data class CurrentUser(
    val firstName: String,
    val lastName: String,
    val roles: List<String>,
    val companyName: String,
    val profilePhotoUrl: String?
)