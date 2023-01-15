package kchat.web.http

import kchat.core.commands.SendMessage
import kchat.core.commands.SendMessageParams
import kchat.web.http.filters.LOGGED_IN_USER_HEADER
import kchat.web.http.filters.Secured
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Status
import org.http4k.core.then
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.springframework.stereotype.Component
import java.util.*

@Component
class HttpRouting(
    private val secured: Secured,
    private val sendMessage: SendMessage
) {

    fun routes() = org.http4k.routing.routes(
        secured.then(postMessage())
    )

    private fun postMessage(): RoutingHttpHandler =
        "message" bind Method.POST to { req ->

            val userId = req.header(LOGGED_IN_USER_HEADER)
                ?.let { UUID.fromString(it) }
                ?: error("User not provided")

            val body = Json.decodeFromString<PostMessageRequest>(req.bodyString())

            val message = sendMessage.execute(SendMessageParams(userId, body.chatId, body.text))
            jsonResponse(Status.OK, message)
        }

}