package kchat.web.http.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Status

class ExpectOkResponse: Filter {

    override fun invoke(next: HttpHandler): HttpHandler = { request: Request ->
        val response = next(request)
        check(response.status == Status.OK) { response.status.toString() }
        response
    }
}