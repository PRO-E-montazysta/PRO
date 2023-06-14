package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.order.OrderListItem

interface IOrderRepository {
    suspend fun getListOfOrders() : Result<List<OrderListItem>>
    suspend fun getOrderDetails(id: Int): Result<Order>
}
