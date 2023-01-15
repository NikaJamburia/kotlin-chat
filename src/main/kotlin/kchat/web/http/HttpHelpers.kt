package kchat.web.http

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status

inline fun <reified T> jsonResponse(status: Status, content: T): Response =
    Response(status)
        .header("content-type", "application/json")
        .body(Json.encodeToString(content))