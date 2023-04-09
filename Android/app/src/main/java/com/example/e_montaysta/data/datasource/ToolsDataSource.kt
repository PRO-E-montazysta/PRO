package com.example.e_montaysta.data.datasource

import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.data.model.ToolType
import com.example.e_montaysta.data.model.Warehouse

class ToolsDataSource (){

    val tools = listOf <Tool>(
            )

    init{

    }
    companion object {
        val toolList = listOf<Tool>(
            Tool(1, "Młotek","asd", ToolType(1, "typ1"), Warehouse(1,"magazyn1"))
        )
    }
}
