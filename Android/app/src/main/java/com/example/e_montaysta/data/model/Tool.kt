package com.example.e_montaysta.data.model

import com.google.gson.Gson

/**
 * Data class that captures tool information
 */
data class Tool(
    val id: Int,
    val name: String,
    val code: String,
    val warehouse: String,
    val category: String
)