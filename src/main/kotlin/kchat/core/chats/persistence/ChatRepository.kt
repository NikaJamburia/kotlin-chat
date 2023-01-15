package kchat.core.chats.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : MongoRepository<ChatDocument, UUID> {
    fun getById(id: UUID): ChatDocument
}