package me.chipnesh.domain.account

import me.chipnesh.domain.*
import java.util.*

data class RegisterAccountRequest(val login: String, val name: String, val email: String, val password: String)
data class RegisterAccountResponse(val id: String)

class RegisterAccount(
        private val accountsGateway: AccountsGateway,
        private val validateRegistrationRequest: ValidateAccountRegistrationRequest,
        private val sendRegisteredNotification: SendRegisteredNotification
) : UseCase<RegisterAccountRequest, RegisterAccountResponse> {

    override fun execute(request: RegisterAccountRequest): Result<RegisterAccountResponse> {
        val result = validateRegistrationRequest.validate(request)
        return when (result) {
            is ValidationResult.Invalid -> Result.Failed(result.messages)
            is ValidationResult.Valid -> {
                val user = createAccount(request)
                val accountId = accountsGateway.add(user)
                notifyRegistration(user)
                Result.Success(RegisterAccountResponse(accountId))
            }
        }
    }

    private fun createAccount(request: RegisterAccountRequest): Account {
        val id = UUID.randomUUID().toString()
        val hash = request.password.md5Hash()
        return Account(id, request.login, request.name, request.email, hash)
    }

    private fun notifyRegistration(account: Account) {
        val notificationRequest = SendRegisteredNotificationRequest(account.email,
                mapOf(
                        "login" to account.login,
                        "name" to account.name
                )
        )
        sendRegisteredNotification.execute(notificationRequest)
    }
}