package com.example.e_montazysta.di

import com.example.e_montazysta.data.controllers.AuthController
import com.example.e_montazysta.data.controllers.Interfaces.IAuthController
import com.example.e_montazysta.data.repository.AuthRepository
import com.example.e_montazysta.data.repository.Interfaces.IAuthRepository
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.data.repository.ToolRepository
import com.example.e_montazysta.ui.toollist.ToolsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IAuthController> { AuthController(get()) }
    single<IAuthRepository> { AuthRepository() }
    single<IToolRepository> { ToolRepository(get()) }

}