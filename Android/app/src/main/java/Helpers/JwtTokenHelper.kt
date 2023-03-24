package Helpers

import com.auth0.android.jwt.JWT

class JwtTokenHelper {
    companion object {
        fun decode(token: String?) : String?{
            if(token == null)
                return null
            var jwt: JWT = JWT(token)
            return jwt.getClaim("scope").asString()
        }
    }
}