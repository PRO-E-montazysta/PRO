package com.example.e_montazysta.ui.order

import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.Interfaces.IOrderRepository
import com.example.e_montazysta.data.repository.Interfaces.IUserRepository
import com.squareup.moshi.Json
import com.example.e_montazysta.data.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

data class OrderDAO(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "typeOfPriority")
    val priority: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "plannedStart")
    val plannedStart: String,
    @Json(name = "plannedEnd")
    val plannedEnd: String,
    @Json(name = "clientId")
    val clientId: Int,
    @Json(name = "foremanId")
    val foremanId: Int,
    @Json(name = "locationId")
    val locationId: Int,
    @Json(name = "managerId")
    val managerId: Int,
    @Json(name = "specialistId")
    val specialistId: Int,
    @Json(name = "salesRepresentativeId")
    val salesRepresentativeId: Int,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "editedAt")
    val editedAt: String?,
    private val userRepository: IUserRepository
){
    suspend fun mapToOrder(): Order {
        val client = getUserDetails(clientId)
        val manager = getUserDetails(managerId)
        val specialist = getUserDetails(specialistId)
        val salesRepresentative = getUserDetails(salesRepresentativeId)
        val foreman = getUserDetails(foremanId)
        return Order(id, name, priority, status, plannedStart, plannedEnd, client, foreman, manager, specialist, salesRepresentative, locationId, createdAt, editedAt)
    }

    private suspend fun getUserDetails(userId: Int): User? {
        val result = userRepository.getUserDetails(userId)
        when (result) {
            is Result.Success -> return result.data
            is Result.Error -> return null
        }
    }
}