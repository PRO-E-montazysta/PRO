package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IElementInWarehouseRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehouseDAO
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehouseSearchCriteria
//import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesDAO
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesDAOItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ElementInWarehouseRepository(
    private val serviceProvider: IServiceProvider
) : IElementInWarehouseRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    override suspend fun getElementInWarehouses(id: String): Result<List<ElementInWarehousesDAOItem>> {
        return try {
            val elementService = serviceProvider.getElementInWarehouseService()
            val elementInWarehouses = elementService.getElementInWarehouses(token, id
//                , searchCriteria.elementId, searchCriteria.minCount, searchCriteria.warehouseId, searchCriteria.elementId
            )
            Result.Success(elementInWarehouses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getElementInWarehouseDetails(id: Int): Result<ElementInWarehouseDAO> {
        return try {
            val elementService = serviceProvider.getElementInWarehouseService()
            val elementInWarehouse = elementService.getElementInWarehouseDetails(token, id.toString())
            Result.Success(elementInWarehouse)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
