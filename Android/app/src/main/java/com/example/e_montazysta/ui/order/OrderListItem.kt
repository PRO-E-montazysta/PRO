package com.example.e_montazysta.ui.order

import com.example.e_montazysta.data.model.Order

data class OrderListItem(
    var id: String,
    var name: String,
    var priority: String,
    var status: String,
    var createdAt: String)

fun Order.mapToOrderItem(): OrderListItem {
    return OrderListItem(id, name, priority, status, createdAt)
}