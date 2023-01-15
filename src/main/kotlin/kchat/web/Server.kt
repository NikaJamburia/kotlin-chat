package kchat.web

import kchat.web.http.filters.HandleDomainErrors
import kchat.web.http.HttpRouting
import kchat.web.ws.WebsocketRouting
import kchat.web.ws.handleWsErrors
import org.http4k.core.then
import org.http4k.filter.CorsPolicy
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.server.Netty
import org.http4k.server.PolyHandler
import org.http4k.server.asServer
import org.http4k.websocket.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class Server(
    @Value("\${http.port}") private val port: Int,
    @Autowired private val httpRouting: HttpRouting,
    @Autowired private val wsRouting: WebsocketRouting
) {

    @EventListener(ApplicationReadyEvent::class)
    fun start() {

        val http = DebuggingFilters.PrintRequest()
            .then(ServerFilters.Cors(CorsPolicy.UnsafeGlobalPermissive))
            .then(HandleDomainErrors())
            .then(httpRouting.routes())

        val webSocket = handleWsErrors()
            .then(wsRouting.routes())

        PolyHandler(http, webSocket).asServer(Netty(port)).start()
        println("Web server started on port $port")
    }
}