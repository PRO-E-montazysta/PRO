package com.example.e_montazysta.data.model

import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


data class Warehouse(
    val companyId: Int,
    val deleted: Boolean,
    val description: String,
    val elementInWarehouses: List<Int>,
    val id: Int,
    val locationId: Int,
    val name: String,
    val openingHours: String,
    val tools: List<Int>
){
    companion object: KoinComponent {
        val warehouseRepository: IWarehouseRepository by inject()
        suspend fun getWarehouseDetails(id: Int) : Warehouse {
            val result = warehouseRepository.getWarehouseDetails(id)
            return when(result){
                is Result.Success -> result.data
                is Result.Error -> throw result.exception
            }
        }
    }
}