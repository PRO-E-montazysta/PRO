package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.warehouse.WarehouseDAO
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import retrofit2.http.*

interface WarehouseService {
    @GET("/api/v1/warehouses/filter")
    suspend fun getFilterWarehouses(
        @Header("Authorization") token: String
    ): List<WarehouseFilterDAO>

    @GET("/api/v1/warehouses/{id}")
    suspend fun getWarehouseDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): WarehouseDAO
}
