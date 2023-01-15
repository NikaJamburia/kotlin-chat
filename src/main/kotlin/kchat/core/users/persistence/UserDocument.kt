package kchat.core.users.persistence

import UUIDSerializer
import kchat.core.users.*
import kchat.core.users.password.BcryptHash
import kchat.core.users.password.Password
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@Serializable
data class UserDocument(
    @Id
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val avatarUrl: String? = null,
    val email: String,
    val passwordHash: String
)

fun UserDocument.toDto() = User(
    id = id,
    name = UsersFullName(firstName, lastName),
    avatar = UserAvatar(avatarUrl),
    email = Email(email),
    password = Password(BcryptHash.usingHashedString(passwordHash))
)