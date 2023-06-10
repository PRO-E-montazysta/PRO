package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.Result

interface IOrderRepository {
    suspend fun getListOfOrders() : Result<List<Order>>
    suspend fun getOrderDetails(id: Int): Result<Order>
}
