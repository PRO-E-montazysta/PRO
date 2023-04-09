package com.example.e_montaysta.data.repository

import com.example.e_montaysta.data.model.Result
import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import org.koin.core.component.KoinComponent

class ToolRepository : IToolRepository, KoinComponent {
    override fun create(login: String, password: String): Result<Tool> {
        TODO("Not yet implemented")
    }

    override fun get(login: String, password: String): Result<Tool> {
        TODO("Not yet implemented")
    }

    override fun update(): Result<Tool> {
        TODO("Not yet implemented")
    }

    override fun delete(): Result<Tool> {
        TODO("Not yet implemented")
    }
}