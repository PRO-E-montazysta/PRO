package Repositories.Interfaces

import com.example.e_montaysta.data.Result
import com.example.e_montaysta.data.model.LoggedInUser

interface IAuthRepository {
    fun login(login: String, password: String) : Result<LoggedInUser>
}