package Repositories


import Helpers.HttpRequestHelper
import Helpers.Interfaces.ISharedPreferencesHelper
import Helpers.JwtTokenHelper
import Models.LoginCredentials
import Models.TokenResponse
import Models.User
import Repositories.Interfaces.IAuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthRepository : IAuthRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()

    override fun login(login: String, password: String) : String {
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
            var roles = JwtTokenHelper.decode(storedToken)
            if(storedToken != null){
                var secondResp = HttpRequestHelper.sendHttpGetRequest<LoginCredentials, List<User>>(
                    "https://dev.emontazysta.pl/api/v1/users/all",
                    creds,
                    rsp
                )
                rsp = secondResp.toString()
            }
        } catch (e : Exception) {
            rsp = e.message.toString()
        }
        return rsp!!
    }
}
