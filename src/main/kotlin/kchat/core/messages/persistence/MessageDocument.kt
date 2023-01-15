package kchat.core.messages.persistence

import UUIDSerializer
import kchat.core.messages.Message
import kchat.core.messages.Receiver
import kchat.core.messages.Sender
import kchat.core.users.persistence.toDto
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@Serializable
data class MessageDocument(
    @Id
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val sender: SenderDocument,
    val content: String,
    val receiver: ReceiverDocument,
)

@Serializable
data class SenderDocument(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID()
)

@Serializable
data class ReceiverDocument(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID()
)

fun MessageDocument.toDto() = Message(
    id,
    Sender(sender.id),
    content,
    Receiver(receiver.id)
)