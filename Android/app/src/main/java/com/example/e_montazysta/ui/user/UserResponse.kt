package com.example.e_montazysta.ui.user

import com.example.e_montazysta.data.model.User

data class UserDAO(
    val active: Boolean,
    val attachments: List<Any>,
    val elementEvents: List<Any>,
    val email: String,
    val employeeComments: List<Any?>,
    val employments: List<Int>,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val notifications: List<Any?>,
    val pesel: String?,
    val phone: String,
    val roles: List<Role>,
    val status: Any?,
    val toolEvents: List<Any?>,
    val unavailabilities: List<Any?>?,
    val unavailableFrom: Any?,
    val unavailableTo: Any?,
    val unavailbilityDescription: Any?,
) {
    fun mapToUser(): User {
        return User(id, firstName, lastName, pesel, roles, email, phone)
    }
}

data class UserFilterDAO(
    val active: Boolean,
    val attachments: List<Int>,
    val elementEvents: List<Int?>?,
    val email: String,
    val employeeComments: List<Int?>?,
    val employments: List<Int?>?,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val notifications: List<Int?>?,
    val pesel: String?,
    val phone: String,
    val roles: List<Role>,
    val status: String,
    val toolEvents: List<Int?>?,
    val unavailabilities: List<Int?>?,
    val unavailableFrom: String?,
    val unavailableTo: String?,
    val unavailbilityDescription: String?,
) {
    fun mapToUserItem(): UserListItem {
        return UserListItem(id, "$firstName $lastName", roles, email, phone)
    }
}

enum class Role(val value: String) {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    SALES_REPRESENTATIVE("Handlowiec"),
    SPECIALIST(" Specjalista"),
    WAREHOUSE_MAN("Magazynier"),
    WAREHOUSE_MANAGER("Kierownik magazynu"),
    FITTER("Montażysta"),
    FOREMAN("Brygadzista")
}
