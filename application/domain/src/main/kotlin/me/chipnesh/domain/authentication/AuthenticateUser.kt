package me.chipnesh.domain.authentication

import me.chipnesh.domain.*
import me.chipnesh.domain.account.AccountsGateway
import java.time.LocalDateTime
import java.util.*

data class AuthenticateUserRequest(val login: String, val password: String)
data class AuthenticateUserResponse(val id: String)

class AuthenticateUser(
        private val sessionsGateway: SessionsGateway,
        private val accountsGateway: AccountsGateway,
        private val validateRequest: ValidateAuthenticationUserRequest
) : UseCase<AuthenticateUserRequest, AuthenticateUserResponse> {

    override suspend fun execute(request: AuthenticateUserRequest): Result<AuthenticateUserResponse> {
        val result = validateRequest.validate(request)
        return when (result) {
            is ValidationResult.Invalid -> Result.Failed(result.messages)
            is ValidationResult.Valid -> {
                val session = sessionsGateway.findActiveByLogin(request.login)
                return if (session == null) {
                    val account = accountsGateway.findByLogin(request.login) ?:
                            return Result.Failed("Account with login '${request.login}' not found")
                    if (wrongPassword(account, request)) return Result.Failed("Wrong password")
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

    private fun createSession(request: AuthenticateUserRequest): Result<AuthenticateUserResponse> {
        val id = sessionsGateway.add(newSession(request.login))
        return Result.Success(AuthenticateUserResponse(id))
    }

    private fun newSession(login: String): Session {
        val id = UUID.randomUUID().toString()
        val expirationTime = LocalDateTime.now().plusDays(1)
        return Session(id, login, true, expirationTime)
    }
}