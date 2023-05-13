package com.example.e_montazysta.ui.order

import com.example.e_montazysta.data.model.Order
import java.text.DateFormat
import java.util.*

data class OrderListItem(
    var id: Int,
    var name: String,
    var priority: String,
    var status: String,
//    var createdAt: String? = ""
)

fun Order.mapToOrderItem(): OrderListItem {
    return OrderListItem(id, name, priority, status)
}