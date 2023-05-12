package com.example.e_montazysta.data.services

interface IServiceProvider {
    fun getToolService(): ToolService
    fun getReleaseService(): ReleaseService
    fun getOrderService(): OrderService
    fun getUserService(): UserService
}