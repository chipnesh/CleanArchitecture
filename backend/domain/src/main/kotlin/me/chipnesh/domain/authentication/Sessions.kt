package me.chipnesh.domain.authentication

import me.chipnesh.domain.Session

interface Sessions {

    suspend fun save(session: Session): Session

    suspend fun findActiveById(id: String): Session?

    suspend fun findActiveByLogin(login: String): Session?

    suspend fun delete(entity: Session)
}