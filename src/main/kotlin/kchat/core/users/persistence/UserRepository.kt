package kchat.core.users.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<UserDocument, UUID> {
    fun getById(id: UUID): UserDocument
}