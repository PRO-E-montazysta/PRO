package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehouseDAO
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesDAOItem

//import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesDAO

interface IElementInWarehouseRepository {
    suspend fun getElementInWarehouses(id: String): Result<List<ElementInWarehousesDAOItem>>
    suspend fun getElementInWarehouseDetails(id: Int): Result<ElementInWarehouseDAO>
}