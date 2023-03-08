package Repositories.Interfaces

interface IAuthRepository {
    fun login(login: String, password: String) : String
}