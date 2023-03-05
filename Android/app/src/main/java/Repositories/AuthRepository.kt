package Repositories

import Helpers.HttpRequestHelper
import Models.LoginCredentials
import Models.TokenResponse
import Models.User
import Repositories.Interfaces.IAuthRepository

class AuthRepository : IAuthRepository {
    override fun login(login: String, password: String) : String {
        val creds = LoginCredentials(login, password)
        var rsp : String? = null;
        try {
             var token  = HttpRequestHelper.sendHttpPostRequest<LoginCredentials, TokenResponse>(
                "https://dev.emontazysta.pl/api/v1/gettoken",
                creds
            )
            rsp = token.getToken()
            if(token != null){
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