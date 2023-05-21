package com.example.e_montazysta.data.services

interface IServiceProvider {
    fun getToolService(): ToolService
    fun getElementService(): ElementService

    fun getReleaseService(): ReleaseService
    fun getOrderService(): OrderService
    fun getUserService(): UserService
    fun getStageService(): StageService
    fun getCommentService(): CommentService

}