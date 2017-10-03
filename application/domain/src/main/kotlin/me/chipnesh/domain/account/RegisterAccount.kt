package me.chipnesh.domain.account

import me.chipnesh.domain.*
import java.util.*

data class RegisterAccountRequest(val login: String, val name: String, val email: String, val password: String)
data class RegisterAccountResponse(val id: String)

class RegisterAccount(
        private val accountsGateway: AccountsGateway,
        private val validateRequest: ValidateRegisterAccountRequest,
        private val sendRegisteredNotification: SendRegisteredNotification
) : UseCase<RegisterAccountRequest, RegisterAccountResponse> {
    override fun execute(request: RegisterAccountRequest): Result<RegisterAccountResponse> {
        val result = validateRequest.validate(request)
        return when (result) {
            is ValidationResult.Invalid -> Result.Failed(result.message)
            is ValidationResult.Valid -> {
                val id = UUID.randomUUID().toString()
                val hash = request.password.md5Hash()

                val user = Account(id, request.login, request.name, request.email, hash)
                val accountId = accountsGateway.add(user)

                notifyRegistration(request)
                Result.Success(RegisterAccountResponse(accountId))
            }
        }
    }

    private fun notifyRegistration(request: RegisterAccountRequest) {
        val notificationRequest = SendRegisteredNotificationRequest(request.email,
                mapOf(
                        "login" to request.login,
                        "name" to request.name
                )
        )
        sendRegisteredNotification.execute(notificationRequest)
    }
}