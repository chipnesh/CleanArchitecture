package me.chipnesh.domain.authentication

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result

data class IsAuthenticatedRequest(val sessionId: String)
data class IsAuthenticatedResponse(val id: String)

class IsAuthenticated(
        private val sessionsGateway: SessionsGateway
) : UseCase<IsAuthenticatedRequest, IsAuthenticatedResponse> {

    override suspend fun execute(request: IsAuthenticatedRequest): Result<IsAuthenticatedResponse> {
        val session = sessionsGateway.findActiveBySessionId(request.sessionId)

        return if (session == null) {
            Result.Failed("Not authenticated")
        } else {
            return if (session.isExpired()) {
                sessionsGateway.remove(session.id)
                Result.Failed("Session expired")
            } else {
                Result.Success(IsAuthenticatedResponse(session.id))
            }
        }
    }
}