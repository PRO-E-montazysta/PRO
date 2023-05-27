package com.example.e_montazysta.data.model

/**
 * Data class that captures tool information
 */
data class Tool(
    val id: Int,
    val name: String,
    val code: String,
    val toolType: String,
    val warehouse: String
)
