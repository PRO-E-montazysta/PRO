package com.example.e_montazysta.data.datasource

import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.model.ToolType
import com.example.e_montazysta.data.model.Warehouse

class ToolsDataSource (){

    val tools = listOf <Tool>(
            )

    init{

    }
    companion object {
        val toolList = listOf<Tool>(
            Tool(1, "Młotek","asd", "1", "1")
        )
    }
}
