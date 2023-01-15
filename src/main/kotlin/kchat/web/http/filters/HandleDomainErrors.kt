package kchat.web.http.filters

import kchat.web.http.jsonResponse
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Status

class HandleDomainErrors: Filter {

    override fun invoke(next: HttpHandler): HttpHandler = { request: Request ->
        try {
            next(request)
        } catch (e: Exception) {
            jsonResponse(Status.BAD_REQUEST, e.message ?: "Error occurred")
        }
    }
}