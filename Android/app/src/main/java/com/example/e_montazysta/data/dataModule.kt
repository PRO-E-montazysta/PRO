package com.example.e_montazysta.data

import com.squareup.moshi.Moshi
import org.koin.dsl.module
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.e_montazysta.data.environments.Environment
import com.example.e_montazysta.data.network.NetworkServiceFactory
import com.example.e_montazysta.data.network.ServiceFactory
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.data.repository.ToolRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.data.services.ServiceProvider
import com.example.e_montazysta.ui.toollist.ToolsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Converter

val dataModule = module {
    factory {
        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        moshi
    }

    single {
        val converterFactory: Converter.Factory = MoshiConverterFactory.create(get())
        converterFactory
    }

    factory { HttpLoggingInterceptor.Level.BODY }

    factory { Environment("https://dev.emontazysta.pl") }


    single {
        val serviceFactory: ServiceFactory = NetworkServiceFactory(
            get(),
            get(),
            get(),
        )
        serviceFactory
    }

    factory {
        val serviceProvider: IServiceProvider = ServiceProvider(get())
        serviceProvider
    }

    factory {
        val toolRepository: IToolRepository =
            ToolRepository(get())
        toolRepository
    }

    viewModel {
        ToolsListViewModel(get())
    }
}