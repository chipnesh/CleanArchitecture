package me.chipnesh.dataprovider.db

import me.chipnesh.domain.Session
import me.chipnesh.domain.authentication.Sessions
import org.springframework.data.mongodb.repository.CoroutineMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionsRepository : CoroutineMongoRepository<Session, String>, Sessions {

    override suspend fun save(session: Session): Session

    override suspend fun findActiveById(id: String): Session?

    override suspend fun findActiveByLogin(login: String): Session?

    override suspend fun delete(entity: Session)
}