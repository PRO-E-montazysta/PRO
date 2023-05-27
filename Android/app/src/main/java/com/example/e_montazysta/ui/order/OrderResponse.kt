package com.example.e_montazysta.ui.order

import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.squareup.moshi.Json
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

data class OrderDAO (
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "typeOfPriority")
    val priority: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "plannedStart")
    val plannedStart: Date?,
    @Json(name = "plannedEnd")
    val plannedEnd: Date?,
    @Json(name = "clientId")
    val clientId: Int?,
    @Json(name = "foremanId")
    val foremanId: Int?,
    @Json(name = "locationId")
    val locationId: Int?,
    @Json(name = "managerId")
    val managerId: Int?,
    @Json(name = "specialistId")
    val specialistId: Int?,
    @Json(name = "salesRepresentativeId")
    val salesRepresentativeId: Int?,
    @Json(name = "createdAt")
    val createdAt: Date?,
    @Json(name = "editedAt")
    val editedAt: Date?
) : KoinComponent {
    val userRepository: IUserRepository by inject()
    suspend fun mapToOrder(): Order {
        var client: User?
        var manager: User?
        var specialist: User?
        var salesRepresentative: User?
        var foreman: User?

        coroutineScope {
            client = async { getUserDetails(clientId) }.await()
            manager = async { getUserDetails(managerId) }.await()
            specialist = async { getUserDetails(specialistId) }.await()
            salesRepresentative = async { getUserDetails(salesRepresentativeId) }.await()
            foreman = async { getUserDetails(foremanId) }.await()
        }
        return Order(id, name, priority, status, plannedStart, plannedEnd, client, foreman, manager, specialist, salesRepresentative, locationId, createdAt, editedAt)
    }

    private suspend fun getUserDetails(userId: Int?): User? {
        if (userId == null) {
            return null
        }
        return when (val result = userRepository.getUserDetails(userId)) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }
}