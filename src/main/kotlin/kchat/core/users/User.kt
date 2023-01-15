package kchat.core.users

import kchat.core.users.password.Password
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: UsersFullName,
    val avatar: UserAvatar,
    val email: Email,
    val password: Password
)

data class UsersFullName(
    val firstName: String,
    val lastName: String
) {
    init {
        check(firstName.isNotBlank() && lastName.isNotBlank()) {
            "Names cant be blank"
        }
    }

    fun asString() = "$firstName $lastName"
}

private const val DEFAULT_USER_AVATAR = "https://not-defined-yet.com"

data class UserAvatar(
    val url: String = DEFAULT_USER_AVATAR
) {
    init {
        check(url.startsWith("https://")) {
            "$url is not a valid url"
        }
    }
}

data class Email(
    val address: String
) {
    init {
        check(address.isValidEmail()) {
            "$address is not a valid email address"
        }
    }

    private fun String.isValidEmail(): Boolean {
        return true
    }
}

