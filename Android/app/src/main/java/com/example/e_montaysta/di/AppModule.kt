package com.example.e_montaysta.di

import com.example.e_montaysta.data.controllers.AuthController
import com.example.e_montaysta.data.controllers.Interfaces.IAuthController
import com.example.e_montaysta.data.repository.AuthRepository
import com.example.e_montaysta.data.repository.Interfaces.IAuthRepository
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import com.example.e_montaysta.data.repository.ToolRepository
import org.koin.dsl.module

val appModule = module {
    single<IAuthController> { AuthController(get()) }
    single<IAuthRepository> { AuthRepository() }
    single<IToolRepository> {ToolRepository() }
}