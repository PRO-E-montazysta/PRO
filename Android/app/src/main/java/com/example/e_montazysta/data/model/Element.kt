package com.example.e_montazysta.data.model

/**
 * Data class that captures tool information
 */
data class Element(
    val id: Int,
    val name: String,
    val code: String,
    val unit: String,
    val qty: Int,
    val warehouse: String
)