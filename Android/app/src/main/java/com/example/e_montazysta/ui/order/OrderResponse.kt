package com.example.e_montazysta.ui.order

import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.ui.client.ClientDAO
import com.example.e_montazysta.ui.location.LocationDAO
import com.squareup.moshi.Json
import org.koin.core.component.KoinComponent
import java.util.Date

data class OrderDAO(
    val id: Int,
    val name: String,
    @Json(name = "typeOfPriority")
    val priority: OrderPriority,
    val status: OrderStatus,
    val plannedStart: Date?,
    val plannedEnd: Date?,
    val companyId: Int?,
    val companyName: String?,
    val clientId: Int?,
    val foremanId: Int?,
    val locationId: Int?,
    val managerId: Int?,
    val managerFirstName: String?,
    val managerLastName: String?,
    val specialistId: Int?,
    val salesRepresentativeId: Int?,
    val createdAt: Date?,
    val editedAt: Date?,
    val orderStages: List<Int>,
    val attachments: List<Int?>
) : KoinComponent {
    suspend fun mapToOrder(): Order {
//        val client = if (clientId != null) User.getUserDetails(clientId) else null
        val manager = if (managerId != null) User.getUserDetails(managerId) else null
        val specialist = if (specialistId != null) User.getUserDetails(specialistId) else null
        val salesRepresentative =
            if (salesRepresentativeId != null) User.getUserDetails(salesRepresentativeId) else null
        val foreman = if (foremanId != null) User.getUserDetails(foremanId) else null
        val location = if (locationId != null) LocationDAO.getLocation(locationId) else null
        val client = if (clientId != null) ClientDAO.getClient(clientId) else null
        return Order(
            id,
            name,
            priority,
            status,
            plannedStart,
            plannedEnd,
            client,
            foreman,
            manager,
            specialist,
            salesRepresentative,
            location,
            orderStages,
            createdAt,
            editedAt
        )
    }
}

enum class OrderStatus(val value: String) {
    CREATED("UTWORZONY"),
    PLANNING("PLANOWANIE"),
    TO_ACCEPT("DO AKCEPTACJI"),
    ACCEPTED("ZAAKCEPTOWANE"),
    IN_PROGRESS("W TRAKCIE"),
    FINISHED("ZAKOŃCZONO")
}

enum class OrderPriority(val value: String) {
    NORMAL("NORMALNY"),
    IMPORTANT("WAŻNY"),
    IMMEDIATE("NATYCHMIASTOWY")
}
