package me.chipnesh.domain.account

import me.chipnesh.domain.Account
import me.chipnesh.domain.ValidationResult
import me.chipnesh.domain.md5Hash
import java.util.*

data class RegisterAccountRequest(
        val login: String,
        val name: String,
        val email: String,
        val password: String
)

sealed class RegisterAccountResponse(val success: Boolean) {
    data class Success(val id: String) : RegisterAccountResponse(true)
    data class Failed(val message: String) : RegisterAccountResponse(false)
}

class RegisterAccount(
        private val accountsGateway: AccountsGateway,
        private val validateRequest: ValidateRegisterAccountRequest
) {
    fun register(request: RegisterAccountRequest): RegisterAccountResponse {
        val result = validateRequest.validate(request)
        return when (result) {
            is ValidationResult.Invalid -> RegisterAccountResponse.Failed(result.message)
            is ValidationResult.Valid -> {
                val id = UUID.randomUUID().toString()
                val hash = request.password.md5Hash()

                val user = Account(id, request.login, request.name, request.email, hash)
                RegisterAccountResponse.Success(accountsGateway.add(user))
            }
        }
    }
}