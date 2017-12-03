package me.chipnesh.entrypoint.rest

import me.chipnesh.domain.Result
import me.chipnesh.domain.account.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.*

data class RegistrationForm(
        val login: String = "",
        val name: String = "",
        val email: String = "",
        val password: String = ""
)

sealed class RegistrationResult(val success: Boolean) {
    data class Success(val id: String): RegistrationResult(true)
    data class Failed(val message: List<String>): RegistrationResult(false)
}
sealed class AccountInfoResult(val success: Boolean) {
    data class Success(val login: String, val name: String, val email: String) : AccountInfoResult(true)
    data class Failed(val message: List<String>) : AccountInfoResult(false)
}

@RestController
@RequestMapping(value = ["/account"], produces = [APPLICATION_JSON_UTF8_VALUE])
class AccountEndpoint(
        private val registerAccount: RegisterAccount,
        private val getAccountInfo: GetAccountInfo
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun register(form: RegistrationForm): RegistrationResult {
        val request = RegisterAccountRequest(form.login, form.name, form.email, form.password)
        val response = registerAccount.execute(request)
        return when (response) {
            is Result.Success -> RegistrationResult.Success(response.result.id)
            is Result.Failed -> RegistrationResult.Failed(response.messages)
        }
    }

    @GetMapping
    suspend fun info(@RequestParam login: String): AccountInfoResult {
        val info = getAccountInfo.execute(GetAccountInfoRequest(login))
        return when (info) {
            is Result.Success -> AccountInfoResult.Success(info.result.login, info.result.name, info.result.email)
            is Result.Failed -> AccountInfoResult.Failed(info.messages)
        }
    }
}