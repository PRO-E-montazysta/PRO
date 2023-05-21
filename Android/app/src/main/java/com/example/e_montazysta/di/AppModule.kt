package com.example.e_montazysta.di

import com.example.e_montazysta.data.controllers.AuthController
import com.example.e_montazysta.data.controllers.Interfaces.IAuthController
import com.example.e_montazysta.data.repository.AuthRepository
import com.example.e_montazysta.data.repository.interfaces.IAuthRepository
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import com.example.e_montazysta.data.repository.ToolRepository
import org.koin.dsl.module

val appModule = module {
    single<IAuthController> { AuthController(get()) }
    single<IAuthRepository> { AuthRepository() }
    single<IToolRepository> { ToolRepository(get()) }

}