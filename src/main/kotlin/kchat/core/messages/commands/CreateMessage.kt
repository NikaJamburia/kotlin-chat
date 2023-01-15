package kchat.core.messages.commands

import kchat.core.Command
import kchat.core.messages.Message
import kchat.core.messages.persistence.*
import org.springframework.stereotype.Component
import java.util.*

data class CreateMessageParams(
    val senderId: UUID,
    val content: String,
    val receiverId: UUID
)

@Component
class CreateMessage(
    private val messageRepository: MessageRepository
) : Command<CreateMessageParams, Message> {

    override fun execute(params: CreateMessageParams): Message =
        messageRepository.save(
            MessageDocument(
                sender = SenderDocument(params.senderId),
                content = params.content,
                receiver = ReceiverDocument(params.receiverId)
            )
        ).toDto()
}