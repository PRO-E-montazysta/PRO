package com.example.e_montazysta.ui.order

import com.example.e_montazysta.helpers.DateUtil

data class OrderListItem(
    val id: Int,
    val name: String,
    val priority: OrderPriority,
    val status: OrderStatus,
    val createdAt: String?,
    val plannedStart: String?,
    val plannedEnd: String?,
    val stagesCount: Int
){
    fun getListItemInfo() : String {
        return "Status: " +status.value + "\nPriorytet: " + priority.value +
                "\nCzas utworzenia: " + createdAt +
                "\nPlanowany start: " + plannedStart +
                "\nPlanowany koniec: " + plannedEnd +
                "\nLiczba etapów: " + stagesCount
    }
}

fun OrderDAO.mapToOrderListItem(): OrderListItem {
    return OrderListItem(
        id,
        name,
        priority,
        status,
        createdAt?.let { DateUtil.format(createdAt) },
        plannedStart?.let { DateUtil.format(plannedStart) },
        plannedEnd?.let { DateUtil.format(plannedEnd) },
        orderStages.size
    )
}