package Models

class LoginCredentials {
    private val password: String?;
    private val username: String?;

    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
}