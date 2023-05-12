package com.example.e_montazysta.data.model

data class Order(
    val id: String,
    val name: String,
    val priority: String,
    val status: String,
    val plannedStart: String,
    val plannedEnd: String,
    val client: User?,
    val foreman: User?,
    val manager: User?,
    val specialistId: User?,
    val salesRepresentativeId: User?,
    val locationId: Int,
    val createdAt: String,
    val editedAt: String?
) {
}
