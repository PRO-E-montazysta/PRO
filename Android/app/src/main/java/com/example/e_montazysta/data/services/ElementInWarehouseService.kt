package com.example.e_montazysta.data.services

import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehouseDAO
//import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesDAO
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesDAOItem
import retrofit2.http.*

interface ElementInWarehouseService {
    @GET("/api/v1/elementInWarehouse/element/{elementId}")
    suspend fun getElementInWarehouses(@Header("Authorization") token: String,
                                       @Path("elementId") elementId : String
//                                       @Query("minCount") minCount : Int? = null,
//                                       @Query("warehouseId") warehouseId : List<String>? = null,
//                                       @Query("elementId") elementIdInQuery : String? = null
    ): List<ElementInWarehousesDAOItem>

    @GET("/api/v1/elementInWarehouse/{id}")
    suspend fun getElementInWarehouseDetails(@Header("Authorization") token: String, @Path("id") id: String?): ElementInWarehouseDAO
}