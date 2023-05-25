package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.IOrderRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OrderRepository (private val serviceProvider: IServiceProvider): IOrderRepository, KoinComponent{
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()

    override suspend fun getListOfOrders(): Result<List<Order>> {
        return try {
            val orderService = serviceProvider.getOrderService()
            val orderDAOs = orderService.getListOfOrders(token)
            val orders = orderDAOs.map { it.mapToOrder() }
            Result.Success(orders)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getOrderDetails(id: Int): Result<Order> {
        return try {
            val orderService = serviceProvider.getOrderService()
            val orderDAO = orderService.getOrderDetail(token, id)
            val orderDetail = orderDAO.mapToOrder()
            Result.Success(orderDetail)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}