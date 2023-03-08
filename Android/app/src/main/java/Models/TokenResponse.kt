package Models

class TokenResponse {
    private var token: String?;

    constructor(token: String?) {
        this.token = token
    }

    fun getToken() : String? {
        return token
    }
}