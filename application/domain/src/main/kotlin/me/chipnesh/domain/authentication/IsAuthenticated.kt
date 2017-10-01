package me.chipnesh.domain.authentication

class IsAuthenticatedRequest(val login: String)

sealed class IsAuthenticatedResponse(open val success: Boolean) {
    data class Authenticated(val id: String) : IsAuthenticatedResponse(true)
    data class NotAuthenticated(val message: String) : IsAuthenticatedResponse(false)
}

class IsAuthenticated(private val sessionsGateway: SessionsGateway) {
    fun isAuthenticated(request: IsAuthenticatedRequest): IsAuthenticatedResponse {
        val session = sessionsGateway.findActiveByLogin(request.login)

        return if (session == null) {
            IsAuthenticatedResponse.NotAuthenticated("Session wasn't created")
        } else {
            return if (session.isExpired()) {
                sessionsGateway.remove(session.id)
                IsAuthenticatedResponse.NotAuthenticated("Session has expired")
            } else {
                IsAuthenticatedResponse.Authenticated(session.id)
            }
        }
    }
}