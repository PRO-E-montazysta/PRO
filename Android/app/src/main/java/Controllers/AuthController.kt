package Controllers

import Controllers.Interfaces.IAuthController
import Repositories.Interfaces.IAuthRepository

class AuthController(private val repository: IAuthRepository) : IAuthController {
    override fun login(login: String, password: String) : String {
        return  repository.login(login, password);
    }
}