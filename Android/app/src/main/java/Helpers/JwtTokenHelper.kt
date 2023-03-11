package Helpers

import com.auth0.android.jwt.JWT

class JwtTokenHelper {
    companion object {
        fun getRole(token: String?) : String?{
            if(token == null)
                return null
            var jwt: JWT = JWT(token)
            return jwt.getClaim("scope").asString()
        }
        fun getUserId(token: String?) : String?{
            if(token == null)
                return null
            var jwt: JWT = JWT(token)
            // TO DO: return jwt.getClaim("scope").asString()
            return "1"
        }


    }
}