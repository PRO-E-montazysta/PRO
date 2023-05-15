package com.example.e_montazysta.data.model

import java.util.*

data class Order(
    val id: Int,
    val name: String,
    val priority: String,
    val status: String,
    val plannedStart: Date,
    val plannedEnd: Date,
    val client: User?,
    val foreman: User?,
    val manager: User?,
    val specialistId: User?,
    val salesRepresentativeId: User?,
    val locationId: Int,
    val createdAt: Date,
    val editedAt: Date?
) {
}
