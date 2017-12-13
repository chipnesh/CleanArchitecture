package me.chipnesh.api

import me.chipnesh.domain.Result
import me.chipnesh.domain.account.GetAccountInfo
import me.chipnesh.domain.account.GetAccountInfoRequest
import me.chipnesh.domain.account.RegisterAccount
import me.chipnesh.domain.account.RegisterAccountRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/account"], produces = [APPLICATION_JSON_UTF8_VALUE])
actual class AccountApi(
        private val registerAccount: RegisterAccount,
        private val getAccountInfo: GetAccountInfo
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    actual suspend fun register(form: RegistrationForm): RegistrationResult {
        val request = RegisterAccountRequest(form.login, form.name, form.email, form.password)
        val response = registerAccount.execute(request)
        return when (response) {
            is Result.Success -> RegistrationResult.Success(response.result.id)
            is Result.Failed -> RegistrationResult.Failed(response.messages)
        }
    }

    @GetMapping
    actual suspend fun info(@RequestParam login: String): AccountInfoResult {
        val info = getAccountInfo.execute(GetAccountInfoRequest(login))
        return when (info) {
            is Result.Success -> AccountInfoResult.Success(info.result.login, info.result.name, info.result.email)
            is Result.Failed -> AccountInfoResult.Failed(info.messages)
        }
    }
}