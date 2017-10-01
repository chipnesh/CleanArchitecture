package me.chipnesh.gateway.rest

import me.chipnesh.domain.authentication.*
import me.chipnesh.domain.authentication.AuthenticateUserResponse.Failed
import me.chipnesh.domain.authentication.AuthenticateUserResponse.Success
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class AuthenticationForm(
        val login: String = "",
        val password: String = ""
)

sealed class AuthenticationResult(val success: Boolean) {
    data class Success(val sessionId: String): AuthenticationResult(true)
    data class Failed(val message: String): AuthenticationResult(false)
}
sealed class IsAuthenticatedResult(val success: Boolean) {
    class Success: IsAuthenticatedResult(true)
    data class Failed(val message: String): IsAuthenticatedResult(false)
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
        val response = authenticateUser.authenticate(request)
        return when (response) {
            is Success -> AuthenticationResult.Success(sessionId = response.id)
            is Failed -> AuthenticationResult.Failed(message = response.message)
        }
    }

    @GetMapping
    fun isAuthenticated(@RequestParam login: String): IsAuthenticatedResult {
        val request = IsAuthenticatedRequest(login)
        val response = isAuthenticated.isAuthenticated(request)
        return when (response) {
            is IsAuthenticatedResponse.NotAuthenticated -> IsAuthenticatedResult.Failed(response.message)
            is IsAuthenticatedResponse.Authenticated -> IsAuthenticatedResult.Success()
        }
    }
}