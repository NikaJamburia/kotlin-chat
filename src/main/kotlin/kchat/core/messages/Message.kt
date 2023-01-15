package kchat.core.messages

import java.util.*

data class Message(
    val id: UUID,
    val sender: Sender,
    val content: String,
    val receiver: Receiver,
)

data class Sender(
    val id: UUID = UUID.randomUUID()
)

data class Receiver(
    val id: UUID = UUID.randomUUID()
)