package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.LoggedInUser
import com.example.e_montazysta.data.model.LoginCredentials
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.TokenResponse
import com.example.e_montazysta.data.repository.Interfaces.IAuthRepository
import com.example.e_montazysta.helpers.HttpRequestHelper
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.helpers.JwtTokenHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class AuthRepository : IAuthRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()

    override fun login(login: String, password: String) : Result<LoggedInUser> {
        val creds = LoginCredentials(login, password)
        var rsp : String? = null
        try {
             var token  = HttpRequestHelper.sendHttpToolRequest<LoginCredentials, TokenResponse>(
                "https://dev.emontazysta.pl/api/v1/gettoken",
                creds
            )
            rsp = token.getToken()
            sharedPreferencesHelper.set("lama", rsp)
            var storedToken =  sharedPreferencesHelper.get("lama")
            var roles = JwtTokenHelper.getRole(storedToken)
            val user = LoggedInUser("Tu będzie ID", storedToken.toString(), roles)

            return Result.Success(user)

            } catch (e: Throwable) {
                return Result.Error(IOException("Error logging in", e))
            }
    }
}
