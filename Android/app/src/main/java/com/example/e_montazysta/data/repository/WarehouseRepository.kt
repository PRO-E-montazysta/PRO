package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Warehouse
import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WarehouseRepository(private val serviceProvider: IServiceProvider) : IWarehouseRepository,
    KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    val warehouseService = serviceProvider.getWarehouseService()

    override suspend fun getWarehouseDetails(id: Int): Result<Warehouse> {
        return try {
            val warehouseDAO = warehouseService.getWarehouseDetails(token, id)
            val warehouses = warehouseDAO.mapToWarehouse()
            Result.Success(warehouses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getListOfWarehouse(): Result<List<WarehouseFilterDAO>> {
        return try {
            val warehouseDAOs = warehouseService.getFilterWarehouses(token)
            Result.Success(warehouseDAOs)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}