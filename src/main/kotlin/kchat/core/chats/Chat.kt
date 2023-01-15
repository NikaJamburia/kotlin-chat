package kchat.core.chats

import java.util.*

data class Chat(
    val id: UUID = UUID.randomUUID(),
    val participants: List<ChatParticipant>
)

data class ChatParticipant(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val avatarUrl: String? = null
)
