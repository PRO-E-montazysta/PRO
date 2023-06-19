package com.example.e_montazysta.data.model

import com.example.e_montazysta.ui.location.LocationDAO
import com.example.e_montazysta.ui.order.OrderPriority
import com.example.e_montazysta.ui.order.OrderStatus
import java.util.Date

data class Order(
    val id: Int,
    val name: String,
    val priority: OrderPriority,
    val status: OrderStatus,
    val plannedStart: Date?,
    val plannedEnd: Date?,
    val client: Int?,
    val foreman: User?,
    val manager: User?,
    val specialist: User?,
    val salesRepresentative: User?,
    val location: LocationDAO?,
    val orderStages: List<Int>,
    val createdAt: Date?,
    val editedAt: Date?
)
