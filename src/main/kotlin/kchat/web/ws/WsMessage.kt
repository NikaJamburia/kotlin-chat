package kchat.web.ws

import kchat.core.messages.Message
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.websocket.WsMessage

@Serializable
data class MessageData<T> (
    val type: MessageType,
    val payload: T
)

fun chatMessageReceived(message: Message): WsMessage = WsMessage(
    Json.encodeToString(
        MessageData(MessageType.MESSAGE_RECEIVED, message)
    )
)

enum class MessageType {
    MESSAGE_RECEIVED
}