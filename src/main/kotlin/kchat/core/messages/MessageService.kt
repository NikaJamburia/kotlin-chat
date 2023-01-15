package kchat.core.messages

import kchat.core.messages.persistence.*
import kchat.core.users.persistence.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    private val messageRepository: MessageRepository,
) {
    fun createMessage(senderId: UUID, receiverId: UUID, text: String): Message =
        messageRepository.save(
            MessageDocument(
                sender = SenderDocument(senderId),
                content = text,
                receiver = ReceiverDocument(receiverId)
            )
        ).toDto()
}