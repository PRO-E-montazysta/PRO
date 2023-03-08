package Models

class TokenResponse {
    private val token: String?;

    constructor(token: String?) {
        this.token = token
    }

    fun getToken() : String? {
        return token
    }
}