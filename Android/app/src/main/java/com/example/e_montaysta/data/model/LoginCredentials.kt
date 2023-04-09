package com.example.e_montaysta.data.model

class LoginCredentials {
    var password: String?;
    var username: String?;

    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
}
