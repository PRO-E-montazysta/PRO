package Controllers.Interfaces

import com.example.e_montaysta.data.Result
import com.example.e_montaysta.data.model.LoggedInUser

interface IAuthController {
    fun login(login: String, password: String) : Result<LoggedInUser>
}