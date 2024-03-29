package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.network.ServiceFactory

class ServiceProvider(private val serviceFactory: ServiceFactory) : IServiceProvider {
    override fun getToolService(): ToolService {
        return serviceFactory.create(ToolService::class.java)
    }

    override fun getElementService(): ElementService {
        return serviceFactory.create(ElementService::class.java)
    }

    override fun getReleaseService(): ReleaseService {
        return serviceFactory.create(ReleaseService::class.java)
    }

    override fun getOrderService(): OrderService {
        return serviceFactory.create(OrderService::class.java)
    }

    override fun getUserService(): UserService {
        return serviceFactory.create(UserService::class.java)
    }

    override fun getStageService(): StageService {
        return serviceFactory.create(StageService::class.java)
    }

    override fun getCommentService(): CommentService {
        return serviceFactory.create(CommentService::class.java)
    }

    override fun getNotificationService(): NotificationService {
        return serviceFactory.create(NotificationService::class.java)
    }

    override fun getEventService(): EventService {
        return serviceFactory.create(EventService::class.java)
    }

    override fun getToolTypeService(): ToolTypeService {
        return serviceFactory.create(ToolTypeService::class.java)
    }

    override fun getWarehouseService(): WarehouseService {
        return serviceFactory.create(WarehouseService::class.java)
    }

    override fun getLocationService(): LocationService {
        return serviceFactory.create(LocationService::class.java)
    }

    override fun getElementInWarehouseService(): ElementInWarehouseService {
        return serviceFactory.create(ElementInWarehouseService::class.java)
    }

    override fun getPlannedItemService(): PlannedItemService {
        return serviceFactory.create(PlannedItemService::class.java)
    }
    override fun getClientService(): ClientService {
        return serviceFactory.create(ClientService::class.java)
    }
}
