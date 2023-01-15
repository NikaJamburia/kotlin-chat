package kchat.core.users.password

import org.springframework.security.crypto.bcrypt.BCrypt

class BcryptHash private constructor(
    private val hashedStringValue: String
) : Hash {

    init {
        check(hashedStringValue.startsWith("\$2a")) {
            "Not a valid Bcrypt hash"
        }
    }

    companion object {
        fun usingHashedString(hashedString: String) = BcryptHash(hashedString)
        fun hashString(string: String) = BcryptHash(BCrypt.hashpw(string, BCrypt.gensalt()))
    }

    override fun asString(): String = hashedStringValue

    override fun matches(str: String): Boolean =
        BCrypt.checkpw(str, hashedStringValue)
}