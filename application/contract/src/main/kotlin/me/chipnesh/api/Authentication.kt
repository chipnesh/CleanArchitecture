package me.chipnesh.api

data class AuthenticationForm(
        val login: String = "",
        val password: String = ""
)

sealed class AuthenticationResult(val success: Boolean) {
    data class Success(val sessionId: String) : AuthenticationResult(true)
    data class Failed(val messages: List<String>) : AuthenticationResult(false)
}

sealed class IsAuthenticatedResult(val success: Boolean) {
    class Success : IsAuthenticatedResult(true)
    data class Failed(val messages: List<String>) : IsAuthenticatedResult(false)
}

expect class AuthenticationApi {
    suspend fun authenticate(form: AuthenticationForm): AuthenticationResult

    suspend fun isAuthenticated(sessionId: String): IsAuthenticatedResult
}
