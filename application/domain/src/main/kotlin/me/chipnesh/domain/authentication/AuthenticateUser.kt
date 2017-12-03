package me.chipnesh.domain.authentication

import me.chipnesh.domain.*
import me.chipnesh.domain.account.Accounts
import java.time.LocalDateTime
import java.util.*

data class AuthenticateUserRequest(val login: String, val password: String)
data class AuthenticateUserResponse(val id: String)

class AuthenticateUser(
        private val sessions: Sessions,
        private val accounts: Accounts,
        private val validateRequest: ValidateAuthenticationUserRequest
) : UseCase<AuthenticateUserRequest, AuthenticateUserResponse> {

    override suspend fun execute(request: AuthenticateUserRequest): Result<AuthenticateUserResponse> {
        val result = validateRequest.validate(request)
        return when (result) {
            is ValidationResult.Invalid -> Result.Failed(result.messages)
            is ValidationResult.Valid -> {
                val session = sessions.findActiveByLogin(request.login)
                return if (session == null) {
                    val account = accounts.findByLogin(request.login) ?:
                            return Result.Failed("Account with login '${request.login}' not found")
                    if (wrongPassword(account, request)) return Result.Failed("Wrong password")
                    createSession(request)
                } else {
                    sessions.delete(session)
                    createSession(request)
                }
            }
        }
    }

    private fun wrongPassword(account: Account, request: AuthenticateUserRequest) =
            account.passwordHash != request.password.md5Hash()

    private suspend fun createSession(request: AuthenticateUserRequest): Result<AuthenticateUserResponse> {
        val id = sessions.save(newSession(request.login)).id
        return Result.Success(AuthenticateUserResponse(id))
    }

    private fun newSession(login: String): Session {
        val id = UUID.randomUUID().toString()
        val expirationTime = LocalDateTime.now().plusDays(1)
        return Session(id, login, true, expirationTime)
    }
}