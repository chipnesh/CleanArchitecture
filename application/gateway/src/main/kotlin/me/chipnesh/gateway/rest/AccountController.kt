package me.chipnesh.gateway.rest

import me.chipnesh.domain.account.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class RegistrationForm(
        val login: String = "",
        val name: String = "",
        val email: String = "",
        val password: String = ""
)

sealed class RegistrationResult(val success: Boolean) {
    data class Success(val id: String): RegistrationResult(true)
    data class Failed(val message: String): RegistrationResult(false)
}
sealed class AccountInfoResult(val success: Boolean) {
    data class Success(val login: String, val name: String, val email: String) : AccountInfoResult(true)
    data class Failed(val message: String) : AccountInfoResult(false)
}

@RestController
@RequestMapping("/account")
class AccountController(
        private val registerAccount: RegisterAccount,
        private val getAccountInfo: GetAccountInfo
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(form: RegistrationForm): RegistrationResult {
        val request = RegisterAccountRequest(form.login, form.name, form.email, form.password)
        val response = registerAccount.register(request)
        return when (response) {
            is RegisterAccountResponse.Success -> RegistrationResult.Success(response.id)
            is RegisterAccountResponse.Failed -> RegistrationResult.Failed(response.message)
        }
    }

    @GetMapping
    fun info(@RequestParam("login") login: String): AccountInfoResult {
        val info = getAccountInfo.getInfo(GetAccountInfoRequest(login))
        return when (info) {
            is GetAccountInfoResponse.Success -> AccountInfoResult.Success(info.login, info.name, info.email)
            is GetAccountInfoResponse.Failed -> AccountInfoResult.Failed(info.message)
        }
    }
}