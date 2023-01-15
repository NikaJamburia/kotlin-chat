package kchat.core.users.password


class Password(
    private val hash: Hash
) {
    fun matches(unHashedString: String): Boolean =
        hash.matches(unHashedString)

    fun asHashedString() = hash.asString()
}

fun String.asBcryptPassword() = Password(BcryptHash.hashString(this))