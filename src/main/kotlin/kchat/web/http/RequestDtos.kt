package kchat.web.http

import java.util.*

data class PostMessageRequest(
    val text: String,
    val chatId: UUID
)