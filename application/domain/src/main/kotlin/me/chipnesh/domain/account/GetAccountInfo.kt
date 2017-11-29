package me.chipnesh.domain.account

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result

data class GetAccountInfoRequest(val login: String)
data class GetAccountInfoResponse(val login: String, val name: String, val email: String)

class GetAccountInfo(
        private val accountsGateway: AccountsGateway
) : UseCase<GetAccountInfoRequest, GetAccountInfoResponse> {

    override suspend fun execute(request: GetAccountInfoRequest): Result<GetAccountInfoResponse> {
        val account = accountsGateway.findByLogin(request.login) ?:
                return Result.Failed("User with login ${request.login} is not found")

        return Result.Success(GetAccountInfoResponse(account.login, account.name, account.email))
    }
}