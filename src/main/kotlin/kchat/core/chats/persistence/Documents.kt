package kchat.core.chats.persistence

import UUIDSerializer
import kchat.core.chats.Chat
import kchat.core.chats.ChatParticipant
import kchat.core.users.persistence.UserDocument
import kchat.core.users.persistence.toDto
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@Serializable
data class ChatDocument(
    @Id
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    @Serializable(with = UUIDSerializer::class)
    val chatRoomId: UUID,
    val participants: List<ChatParticipantDocument>
)

@Serializable
data class ChatParticipantDocument(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val avatarUrl: String? = null
)

fun ChatParticipantDocument.toDto() = ChatParticipant(id, name, avatarUrl)

fun ChatDocument.toDto() = Chat(id, participants.map(ChatParticipantDocument::toDto))