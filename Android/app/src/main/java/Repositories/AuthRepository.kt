package Repositories

import Repositories.Interfaces.IAuthRepository

class AuthRepository : IAuthRepository {
    override fun login(login: String, password: String) : String {
        return "User"
    }
}