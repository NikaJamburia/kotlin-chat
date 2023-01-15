package kchat.web.ws

import org.http4k.websocket.WsFilter
import org.http4k.websocket.WsMessage

fun handleWsErrors() = WsFilter { next ->
    {
        try {
            next(it)
        } catch (e: Exception) {
            it.send(WsMessage(e.message ?: "Error occured"))
            it.close()
        }
    }
}