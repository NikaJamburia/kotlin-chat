package kchat.core.chats

import kchat.core.messages.Message
import kchat.core.chats.persistence.ChatRepository
import kchat.core.chats.persistence.toDto
import kchat.core.users.User
import org.springframework.stereotype.Component

@Component
class LiveChat(
    private val chatRepository: ChatRepository
) {
    private val userChannels = mutableListOf<ChatParticipantMessageChannel>()

    fun registerParticipant(userChannel: ChatParticipantMessageChannel) {
        userChannels.add(userChannel)
    }

    fun removeParticipant(participant: ChatParticipant) {
        getChannelFor(participant)?.let {
            it.close()
            userChannels.remove(it)
        }
    }

    fun notifyMessageReceivers(message: Message) {
        val chat = chatRepository.getById(message.receiver.id).toDto()

        chat.participants
            .filter { it.id != message.sender.id }
            .mapNotNull { getChannelFor(it) }
            .forEach { it.send(message) }
    }

    private fun getChannelFor(chatParticipant: ChatParticipant): ChatParticipantMessageChannel? =
        userChannels.firstOrNull { channel -> channel.participant.id == chatParticipant.id }

}