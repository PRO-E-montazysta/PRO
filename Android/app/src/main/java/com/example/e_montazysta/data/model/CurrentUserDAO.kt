package com.example.e_montazysta.data.model

data class CurrentUserDAO(
    val firstName: String,
    val lastName: String,
    val roles: List<String>,
    val companyName: String,
    val profilePhotoUrl: String?

)