package com.example.e_montazysta.ui.user

data class UserListItem(
    val id: Int,
    val name: String,
    val roles: List<Role>,
    val email: String,
    val phone: String
){
    fun getItemInfo(): String {
        return "${roles.joinToString (", ") { it.value }}" +
                "\n$email" +
                "\n$phone"
    }
}


