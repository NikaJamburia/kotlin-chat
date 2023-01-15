package kchat.core.chats

import kchat.core.messages.Message
import kchat.core.users.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class ChatParticipantMessageChannel(
    val participant: ChatParticipant,
    private val coroutineScope: CoroutineScope
) {
    private val channel = Channel<Message>()
    private var msgHandlers: MutableList<MessageHandler> = mutableListOf()

    private val job = coroutineScope.launch {
        for (msg in channel) {
            msgHandlers.forEach { it(msg) }
        }
    }


    fun onMessageReceived(handler: MessageHandler) {
        msgHandlers.add(handler)
    }

    fun send(msg: Message) {
        coroutineScope.launch {
            channel.send(msg)
        }
    }

    fun close() {
        channel.close()
        job.cancel()
    }
}

private typealias MessageHandler = (message: Message) -> Unit