package me.chipnesh.domain.authentication

import me.chipnesh.domain.Account
import me.chipnesh.domain.Session
import me.chipnesh.domain.ValidationResult
import me.chipnesh.domain.account.AccountsGateway
import me.chipnesh.domain.md5Hash
import java.time.LocalDateTime
import java.util.*

class AuthenticateUserRequest(val login: String, val password: String)

sealed class AuthenticateUserResponse(val success: Boolean) {
    data class Success(val id: String) : AuthenticateUserResponse(true)
    data class Failed(val message: String) : AuthenticateUserResponse(false)
}

class AuthenticateUser(
        private val sessionsGateway: SessionsGateway,
        private val accountsGateway: AccountsGateway,
        private val validateRequest: ValidateAuthenticateUserRequest
) {
    fun authenticate(request: AuthenticateUserRequest): AuthenticateUserResponse {
        val result = validateRequest.validate(request)
        return when (result) {
            is ValidationResult.Invalid -> AuthenticateUserResponse.Failed(result.message)
            is ValidationResult.Valid -> {
                val session = sessionsGateway.findActiveByLogin(request.login)
                return if (session == null) {
                    val account = accountsGateway.findByLogin(request.login) ?:
                            return AuthenticateUserResponse.Failed("Account with login '${request.login}' not found")

                    if (wrongPassword(account, request))
                        return AuthenticateUserResponse.Failed("Wrong password")

                    createSession(request)
                } else {
                    sessionsGateway.remove(session.id)
                    createSession(request)
                }
            }
        }
    }

    private fun wrongPassword(account: Account, request: AuthenticateUserRequest) =
            account.passwordHash != request.password.md5Hash()

    private fun createSession(request: AuthenticateUserRequest): AuthenticateUserResponse.Success {
        val id = sessionsGateway.add(newSession(request.login))
        return AuthenticateUserResponse.Success(id)
    }

    private fun newSession(login: String): Session {
        val id = UUID.randomUUID().toString()
        val expirationTime = LocalDateTime.now().plusDays(1)
        return Session(id, login, true, expirationTime)
    }
}