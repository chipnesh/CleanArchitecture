package me.chipnesh.domain.authentication

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result

data class IsAuthenticatedRequest(val login: String)
data class IsAuthenticatedResponse(val id: String)

class IsAuthenticated(
        private val sessionsGateway: SessionsGateway
) : UseCase<IsAuthenticatedRequest, IsAuthenticatedResponse> {
    override fun execute(request: IsAuthenticatedRequest): Result<IsAuthenticatedResponse> {
        val session = sessionsGateway.findActiveByLogin(request.login)

        return if (session == null) {
            Result.Failed("Session wasn't created")
        } else {
            return if (session.isExpired()) {
                sessionsGateway.remove(session.id)
                Result.Failed("Session has expired")
            } else {
                Result.Success(IsAuthenticatedResponse(session.id))
            }
        }
    }
}