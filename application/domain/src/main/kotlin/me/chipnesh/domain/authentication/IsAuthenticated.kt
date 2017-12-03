package me.chipnesh.domain.authentication

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result

data class IsAuthenticatedRequest(val sessionId: String)
data class IsAuthenticatedResponse(val id: String)

class IsAuthenticated(
        private val sessions: Sessions
) : UseCase<IsAuthenticatedRequest, IsAuthenticatedResponse> {

    override suspend fun execute(request: IsAuthenticatedRequest): Result<IsAuthenticatedResponse> {
        val session = sessions.findActiveById(request.sessionId)

        return if (session == null) {
            Result.Failed("Not authenticated")
        } else {
            return if (session.isExpired()) {
                sessions.delete(session)
                Result.Failed("Session expired")
            } else {
                Result.Success(IsAuthenticatedResponse(session.id))
            }
        }
    }
}