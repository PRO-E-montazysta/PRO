package com.example.e_montazysta.data.model

import java.util.Date

data class ToolDAO(
    val id: Int,
    val name: String,
    val code: String,
    val createdAt: Date,
    val toolReleases: List<Int>,
    val warehouseId: Int,
    val toolEvents: List<Int>,
    val toolTypeId: Int,
    val status: toolStatus
) {
    suspend fun mapToTool(): Tool {
        val toolType = ToolType.getToolType(toolTypeId)
        val warehouse = Warehouse.getWarehouseDetails(warehouseId)
//        val toolEvents: List<Event> = toolEvents.map { Event.getEventDetails(it) }
//        val toolReleases: List<Release> = toolReleases.map { Release.getReleaseDetails(it) }
        return Tool(id, name, code, createdAt, toolReleases, warehouse, toolEvents, toolType, status)
    }
    enum class toolStatus(val value: String){
        AVAILABLE("Dostępne"),
        RELEASED("Wydane"),
        IN_REPAIR("W naprawie"),
        DELETED("Usunięte")
    }
}
