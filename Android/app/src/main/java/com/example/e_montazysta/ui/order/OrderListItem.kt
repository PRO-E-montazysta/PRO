package com.example.e_montazysta.ui.order

import com.example.e_montazysta.helpers.DateUtil

data class OrderListItem(
    val id: Int,
    val name: String,
    val priority: OrderPriority,
    val status: OrderStatus,
    val createdAt: String,
    val plannedStart: String,
    val plannedEnd: String?,
    val stagesCount: Int
) {
    fun getListItemInfo(): String {
        return "Status: " + status.value + "\nPriorytet: " + priority.value +
                "\nCzas utworzenia: " + createdAt +
                "\nPlanowany start: " + (if (!(plannedStart.isNullOrBlank())) plannedStart else "Brak") +
                "\nPlanowany koniec: " + (if (!(plannedEnd.isNullOrBlank())) plannedStart else "Brak") +
                "\nLiczba etapów: " + stagesCount
    }
}

fun OrderDAO.mapToOrderListItem(): OrderListItem {
    val plannedStart = if (plannedStart != null) DateUtil.format(plannedStart) else "Brak"
    val plannedEnd = if (plannedEnd != null) DateUtil.format(plannedEnd) else "Brak"
    val createdAt = if (createdAt != null) DateUtil.format(createdAt) else "Brak"
    return OrderListItem(
        id,
        name,
        priority,
        status,
        createdAt,
        plannedStart,
        plannedEnd,
        orderStages.size
    )
}