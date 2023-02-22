package Controllers.Interfaces

interface IAuthController {
    fun login(login: String, password: String) : String
}