package kchat.web.ws

class WsCommand(
    val commandType: WsCommandType,
    val dataJson: String
)

enum class WsCommandType {
    SEND_MESSAGE
}

