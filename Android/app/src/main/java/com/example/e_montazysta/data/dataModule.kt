package com.example.e_montazysta.data

import com.example.e_montazysta.data.environments.Environment
import com.example.e_montazysta.data.network.NetworkServiceFactory
import com.example.e_montazysta.data.network.ServiceFactory
import com.example.e_montazysta.data.repository.ClientRepository
import com.example.e_montazysta.data.repository.CommentRepository
import com.example.e_montazysta.data.repository.ElementInWarehouseRepository
import com.example.e_montazysta.data.repository.ElementRepository
import com.example.e_montazysta.data.repository.EventRepository
import com.example.e_montazysta.data.repository.LocationRepository
import com.example.e_montazysta.data.repository.NotificationRepository
import com.example.e_montazysta.data.repository.OrderRepository
import com.example.e_montazysta.data.repository.PlannedItemRepository
import com.example.e_montazysta.data.repository.ReleaseRepository
import com.example.e_montazysta.data.repository.StageRepository
import com.example.e_montazysta.data.repository.ToolRepository
import com.example.e_montazysta.data.repository.ToolTypeRepository
import com.example.e_montazysta.data.repository.UserRepository
import com.example.e_montazysta.data.repository.WarehouseRepository
import com.example.e_montazysta.data.repository.interfaces.IClientRepository
import com.example.e_montazysta.data.repository.interfaces.ICommentRepository
import com.example.e_montazysta.data.repository.interfaces.IElementInWarehouseRepository
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import com.example.e_montazysta.data.repository.interfaces.IEventRepository
import com.example.e_montazysta.data.repository.interfaces.ILocationRepository
import com.example.e_montazysta.data.repository.interfaces.INotificationRepository
import com.example.e_montazysta.data.repository.interfaces.IOrderRepository
import com.example.e_montazysta.data.repository.interfaces.IPlannedItemRepository
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import com.example.e_montazysta.data.repository.interfaces.IStageRepository
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import com.example.e_montazysta.data.repository.interfaces.IToolTypeRepository
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.data.services.ServiceProvider
import com.example.e_montazysta.helpers.BigDecimalAdapter
import com.example.e_montazysta.helpers.CustomDateAdapter
import com.example.e_montazysta.ui.element.ElementDetailViewModel
import com.example.e_montazysta.ui.element.ElementsListViewModel
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehouseDetailViewModel
import com.example.e_montazysta.ui.element_in_warehouse.ElementInWarehousesListViewModel
import com.example.e_montazysta.ui.event.EventDetailViewModel
import com.example.e_montazysta.ui.event.EventListViewModel
import com.example.e_montazysta.ui.notification.NotificationListViewModel
import com.example.e_montazysta.ui.order.OrderDetailViewModel
import com.example.e_montazysta.ui.order.OrderListViewModel
import com.example.e_montazysta.ui.release.ReleaseCreateViewModel
import com.example.e_montazysta.ui.release.ReleaseDetailViewModel
import com.example.e_montazysta.ui.release.ReleaseListViewModel
import com.example.e_montazysta.ui.returnitem.ReturnCreateViewModel
import com.example.e_montazysta.ui.stage.StageDetailViewModel
import com.example.e_montazysta.ui.stage.StageListViewModel
import com.example.e_montazysta.ui.toollist.ToolDetailViewModel
import com.example.e_montazysta.ui.toollist.ToolsListViewModel
import com.example.e_montazysta.ui.user.UserDetailViewModel
import com.example.e_montazysta.ui.user.UserListViewModel
import com.example.e_montazysta.ui.viewmodels.DashboardViewModel
import com.example.e_montazysta.ui.viewmodels.QRScanViewModel
import com.example.e_montazysta.ui.warehouse.WarehouseDetailViewModel
import com.example.e_montazysta.ui.warehouse.WarehouseListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal
import java.util.Date

val dataModule = module {
    factory {
        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, CustomDateAdapter().nullSafe())
                .add(BigDecimal::class.java, BigDecimalAdapter().nullSafe())
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

    factory {
        val stageRepository: IStageRepository =
            StageRepository(get())
        stageRepository
    }

    factory {
        val commentRepository: ICommentRepository =
            CommentRepository(get())
        commentRepository
    }

    factory {
        val elementRepository: IElementRepository =
            ElementRepository(get())
        elementRepository
    }
    factory {
        val notificationRepository: INotificationRepository =
            NotificationRepository(get())
        notificationRepository
    }

    factory {
        val eventRepository: IEventRepository =
            EventRepository(get())
        eventRepository
    }

    factory {
        val toolTypeRepository: IToolTypeRepository =
            ToolTypeRepository(get())
        toolTypeRepository
    }

    factory {
        val warehouseRepository: IWarehouseRepository =
            WarehouseRepository(get())
        warehouseRepository
    }

    factory {
        val locationRepository: ILocationRepository =
            LocationRepository(get())
        locationRepository
    }

    factory {
        val elementInWarehouseRepository: IElementInWarehouseRepository =
            ElementInWarehouseRepository(get())
        elementInWarehouseRepository
    }

    factory {
        val plannedItemRepository: IPlannedItemRepository =
            PlannedItemRepository(get())
        plannedItemRepository
    }

    factory {
        val clientRepository: IClientRepository =
            ClientRepository(get())
        clientRepository
    }

    viewModel {
        ToolsListViewModel(get())
    }
    viewModel {
        ToolDetailViewModel(get())
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

    viewModel {
        ReleaseCreateViewModel()
    }
    viewModel {
        ElementsListViewModel(get())
    }
    viewModel {
        ElementDetailViewModel(get())
    }
    viewModel {
        DashboardViewModel(get())
    }
    viewModel {
        NotificationListViewModel(get())
    }
    viewModel {
        EventListViewModel(get())
    }
    viewModel {
        EventDetailViewModel(get())
    }
    viewModel {
        WarehouseListViewModel(get())
    }
    viewModel {
        WarehouseDetailViewModel(get())
    }
    viewModel {
        ElementInWarehouseDetailViewModel(get())
    }
    viewModel {
        ElementInWarehousesListViewModel(get())
    }
    viewModel {
        UserDetailViewModel(get())
    }
    viewModel {
        UserListViewModel(get())
    }
    viewModel {
        ReturnCreateViewModel()
    }
    viewModel {
        QRScanViewModel()
    }
}
