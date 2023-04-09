package com.example.e_montaysta.data.datasource

import com.example.e_montaysta.data.model.Tool

class ToolsDataSource (){

    val tools = listOf <Tools>(
            )

    init{

    }
    companion object {
        val toolList = listOf<Tool>(
            Tool("Młotek", 1, "Młotki")
        )
    }
}
