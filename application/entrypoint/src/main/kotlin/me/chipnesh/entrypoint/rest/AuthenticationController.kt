package me.chipnesh.entrypoint.rest

import me.chipnesh.domain.Result
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.AuthenticateUserRequest
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.authentication.IsAuthenticatedRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class AuthenticationForm(
        val login: String = "",
        val password: String = ""
)

sealed class AuthenticationResult(val success: Boolean) {
    data class Success(val sessionId: String) : AuthenticationResult(true)
    data class Failed(val message: List<String>) : AuthenticationResult(false)
}

sealed class IsAuthenticatedResult(val success: Boolean) {
    class Success : IsAuthenticatedResult(true)
    data class Failed(val message: List<String>) : IsAuthenticatedResult(false)
}

@RestController
@RequestMapping("/authentication")
class AuthenticationController(
        private val authenticateUser: AuthenticateUser,
        private val isAuthenticated: IsAuthenticated
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun authenticate(form: AuthenticationForm): AuthenticationResult {
        val request = AuthenticateUserRequest(form.login, form.password)
        val response = authenticateUser.execute(request)
        return when (response) {
            is Result.Success -> AuthenticationResult.Success(response.result.id)
            is Result.Failed -> AuthenticationResult.Failed(response.messages)
        }
    }

    @GetMapping
    fun isAuthenticated(@RequestParam sessionId: String): IsAuthenticatedResult {
        val request = IsAuthenticatedRequest(sessionId)
        val response = isAuthenticated.execute(request)
        return when (response) {
            is Result.Success -> IsAuthenticatedResult.Success()
            is Result.Failed -> IsAuthenticatedResult.Failed(response.messages)
        }
    }
}