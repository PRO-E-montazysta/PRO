package Repositories

import Helpers.HttpRequestHelper
import Helpers.Interfaces.ISharedPreferencesHelper
import Helpers.JwtTokenHelper
import Models.LoginCredentials
import Models.TokenResponse
import Models.User
import Repositories.Interfaces.IAuthRepository
import com.example.e_montaysta.data.model.LoggedInUser
import com.example.e_montaysta.data.Result
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class AuthRepository : IAuthRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()

    override fun login(login: String, password: String) : Result<LoggedInUser> {
        val creds = LoginCredentials(login, password)
        var rsp : String? = null;
        try {
             var token  = HttpRequestHelper.sendHttpPostRequest<LoginCredentials, TokenResponse>(
                "https://dev.emontazysta.pl/api/v1/gettoken",
                creds
            )
            rsp = token.getToken()
            sharedPreferencesHelper.set("lama", rsp)
            var storedToken = sharedPreferencesHelper.get("lama")
            var roles = JwtTokenHelper.getRole(storedToken)
            val user = LoggedInUser("Tu będzie ID", storedToken.toString(), roles)

            return Result.Success(user)

            } catch (e: Throwable) {
                return Result.Error(IOException("Error logging in", e))
            }
    }
}
