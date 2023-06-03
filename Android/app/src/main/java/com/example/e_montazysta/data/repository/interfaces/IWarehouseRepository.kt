package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Warehouse
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO

interface IWarehouseRepository {
    suspend fun getWarehouseDetails(id: Int): Result<Warehouse>
    suspend fun getListOfWarehouse(): Result<List<WarehouseFilterDAO>>
}
