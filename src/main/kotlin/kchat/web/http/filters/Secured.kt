package kchat.web.http.filters

import kchat.core.users.AuthorizationService
import org.http4k.core.*
import org.springframework.stereotype.Component

@Component
class Secured(
    private val authorizationService: AuthorizationService
): Filter {

    override fun invoke(next: HttpHandler): HttpHandler = { request: Request ->
        request.header("user-token")
            ?.let { authorizationService.getUserId(it) }
            ?.let { next(request.header(LOGGED_IN_USER_HEADER, it.toString())) }
            ?: Response(Status.FORBIDDEN)
    }
}

const val LOGGED_IN_USER_HEADER = "logged-in-user-id"