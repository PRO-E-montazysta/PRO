package com.example.e_montaysta.di

import com.example.e_montaysta.data.api.ServiceApi
import com.example.e_montaysta.data.controllers.AuthController
import com.example.e_montaysta.data.controllers.Interfaces.IAuthController
import com.example.e_montaysta.data.repository.AuthRepository
import com.example.e_montaysta.data.repository.Interfaces.IAuthRepository
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import com.example.e_montaysta.data.repository.ToolRepository
import com.example.e_montaysta.helpers.RetrofitHelper
import com.example.e_montaysta.ui.viewmodels.ToolsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<IAuthController> { AuthController(get()) }
    single<IAuthRepository> { AuthRepository() }
    single {
        Retrofit.Builder().baseUrl(RetrofitHelper.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
            .create(ServiceApi::class.java)
    }
    single<IToolRepository> { ToolRepository(get()) }
    viewModel {
        ToolsViewModel(get())
    }
}