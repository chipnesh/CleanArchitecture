package me.chipnesh.domain.authentication

import me.chipnesh.domain.Session

interface SessionsGateway {
    fun add(session: Session): String
    fun findActiveBySessionId(sessionId: String): Session?
    fun findActiveByLogin(login: String): Session?
    fun remove(id: String)
}