package com.example.e_montaysta.data.model

class TokenResponse {
    private var token: String?;

    constructor(token: String?) {
        this.token = token
    }

    fun getToken() : String? {
        return token
    }
}
