package Repositories

import Enums.RequestType
import Helpers.HttpRequestHelper
import Models.LoginCredentials
import Models.TokenResponse
import Repositories.Interfaces.IAuthRepository

class AuthRepository : IAuthRepository {
    override fun login(login: String, password: String) : String {
        val creds = LoginCredentials(login, password)
        var rsp : String? = null;
        try {
             var token  = HttpRequestHelper.sendHttpRequest<LoginCredentials, TokenResponse>(
                RequestType.POST,
                "https://dev.emontazysta.pl/api/v1/gettoken",
                creds
            )
            rsp = token.getToken()
            if(token != null){
                var secondResp = HttpRequestHelper.sendHttpRequest<>(
                    RequestType.GET,
                    "",
                    query,
                    token
                )
            }
        } catch (e : Throwable) {
            rsp = e.message.toString()
        }
        return rsp!!
    }
}