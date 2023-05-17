package com.example.e_montazysta.data

import com.example.e_montazysta.data.environments.Environment
import com.example.e_montazysta.data.network.NetworkServiceFactory
import com.example.e_montazysta.data.network.ServiceFactory
import com.example.e_montazysta.data.repository.Interfaces.IOrderRepository
import com.example.e_montazysta.data.repository.Interfaces.IReleaseRepository
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.data.repository.Interfaces.IUserRepository
import com.example.e_montazysta.data.repository.OrderRepository
import com.example.e_montazysta.data.repository.ReleaseRepository
import com.example.e_montazysta.data.repository.ToolRepository
import com.example.e_montazysta.data.repository.UserRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.data.services.ServiceProvider
import com.example.e_montazysta.helpers.CustomDateAdapter
import com.example.e_montazysta.ui.order.OrderDetailViewModel
import com.example.e_montazysta.ui.order.OrderListViewModel
import com.example.e_montazysta.ui.release.ReleaseDetailViewModel
import com.example.e_montazysta.ui.release.ReleaseListViewModel
import com.example.e_montazysta.ui.stage.StageDetailViewModel
import com.example.e_montazysta.ui.stage.StageListViewModel
import com.example.e_montazysta.ui.toollist.ToolsListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val dataModule = module {
    factory {
        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, CustomDateAdapter().nullSafe())
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

    factory {
        val releaseRepository: IReleaseRepository =
            ReleaseRepository(get())
        releaseRepository
    }

    factory {
        val orderRepository: IOrderRepository =
            OrderRepository(get())
        orderRepository
    }

    factory {
        val userRepository: IUserRepository =
            UserRepository(get())
        userRepository
    }

    viewModel {
        ToolsListViewModel(get())
    }
    viewModel {
        ReleaseListViewModel(get())
    }
    viewModel {
        ReleaseDetailViewModel(get())
    }
    viewModel {
        OrderListViewModel(get())
    }
    viewModel {
        OrderDetailViewModel(get())
    }
    viewModel {
        StageListViewModel(get())
    }
    viewModel {
        StageDetailViewModel(get())
    }
}