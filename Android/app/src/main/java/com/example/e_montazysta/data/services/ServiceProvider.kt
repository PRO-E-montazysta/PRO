package com.example.e_montazysta.data.services

import com.example.e_montazysta.data.network.ServiceFactory

class ServiceProvider (private val serviceFactory: ServiceFactory) : IServiceProvider {
    override fun getToolService(): ToolService {
        return serviceFactory.create(ToolService::class.java)
    }
}