package kchat.core.users.password

interface Hash {
    fun asString(): String
    fun matches(str: String): Boolean
}