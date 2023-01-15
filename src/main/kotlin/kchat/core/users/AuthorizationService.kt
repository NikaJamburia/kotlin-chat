package kchat.core.users

import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthorizationService {
    fun getUserId(token: String): UUID? = UUID.fromString(token)
}