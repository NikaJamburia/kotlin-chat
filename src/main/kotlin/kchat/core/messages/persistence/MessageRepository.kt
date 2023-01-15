package kchat.core.messages.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface MessageRepository: MongoRepository<MessageDocument, UUID> {
    fun getById(id: UUID): MessageDocument
}