package me.chipnesh.api

import me.chipnesh.domain.Result
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.AuthenticateUserRequest
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.authentication.IsAuthenticatedRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/authentication"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
actual class AuthenticationApi(
        private val authenticateUser: AuthenticateUser,
        private val isAuthenticated: IsAuthenticated
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    actual suspend fun authenticate(form: AuthenticationForm): AuthenticationResult {
        val request = AuthenticateUserRequest(form.login, form.password)
        val response = authenticateUser.execute(request)
        return when (response) {
            is Result.Success -> AuthenticationResult.Success(response.result.id)
            is Result.Failed -> AuthenticationResult.Failed(response.messages)
        }
    }

    @GetMapping
    actual suspend fun isAuthenticated(@RequestParam sessionId: String): IsAuthenticatedResult {
        val request = IsAuthenticatedRequest(sessionId)
        val response = isAuthenticated.execute(request)
        return when (response) {
            is Result.Success -> IsAuthenticatedResult.Success()
            is Result.Failed -> IsAuthenticatedResult.Failed(response.messages)
        }
    }
}