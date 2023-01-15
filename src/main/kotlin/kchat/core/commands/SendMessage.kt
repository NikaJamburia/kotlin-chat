package kchat.core.commands

import kchat.core.Command
import kchat.core.chats.LiveChat
import kchat.core.messages.Message
import kchat.core.messages.MessageService
import kchat.core.messages.commands.CreateMessage
import kchat.core.messages.commands.CreateMessageParams
import org.springframework.stereotype.Component
import java.util.*

data class SendMessageParams(
    val senderId: UUID,
    val chatId: UUID,
    val text: String
)

@Component
class SendMessage(
    private val liveChat: LiveChat,
    private val createMessage: CreateMessage
): Command<SendMessageParams, Message> {

    override fun execute(params: SendMessageParams): Message {
        val message = createMessage.execute(
            CreateMessageParams(
                senderId = params.senderId,
                receiverId = params.chatId,
                content = params.text
            )
        )
        liveChat.notifyMessageReceivers(message)
        return message
    }
}