package kchat.web.ws

import kchat.core.chats.ChatParticipant
import kchat.core.chats.LiveChat
import kchat.core.chats.ChatParticipantMessageChannel
import kchat.core.users.User
import kchat.core.users.persistence.UserDocument
import kchat.core.users.persistence.UserRepository
import kchat.core.users.persistence.toDto
import kotlinx.coroutines.runBlocking
import org.http4k.routing.bind
import org.http4k.routing.websockets
import org.http4k.websocket.WsConsumer
import org.springframework.stereotype.Component
import java.util.*

@Component
class WebsocketRouting(
    private val userRepository: UserRepository,
    private val liveChat: LiveChat
) {

    fun routes()  = websockets(
        "/live-chat" bind wsCarfaxPublisher()
    )

    private fun wsCarfaxPublisher(): WsConsumer = { ws ->

        runBlocking {
            val chatParticipant = ws.upgradeRequest.header("user-id")
                ?.let { userRepository.getById(UUID.fromString(it)) }
                ?.let(UserDocument::toDto)
                ?.let(User::asChatParticipant)
                ?: error("No user provided")

            val userChannel = ChatParticipantMessageChannel(chatParticipant, this)
            userChannel.onMessageReceived {
                ws.send(chatMessageReceived(it))
            }

            liveChat.registerParticipant(userChannel)

            ws.onClose {
                liveChat.removeParticipant(chatParticipant)
            }

        }
    }

}

private fun User.asChatParticipant() = ChatParticipant(id, name.asString(), avatar.url)

