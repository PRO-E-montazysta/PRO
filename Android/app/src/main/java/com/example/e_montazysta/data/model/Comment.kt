package com.example.e_montazysta.data.model

import java.util.Date

data class Comment(
    val attachments: List<Int>,
    val content: String,
    val createdAt: Date,
    val deleted: Boolean,
    val id: Int,
    val messageCreator: User?,
    val orderStageId: Int
)