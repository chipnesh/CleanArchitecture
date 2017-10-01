package me.chipnesh.domain.account

class GetAccountInfoRequest(val login: String)

sealed class GetAccountInfoResponse {
    data class Success(
            val login: String,
            val name: String,
            val email: String
    ): GetAccountInfoResponse()
    data class Failed(val message: String): GetAccountInfoResponse()
}

class GetAccountInfo(
        private val accountsGateway: AccountsGateway
) {
    fun getInfo(request: GetAccountInfoRequest): GetAccountInfoResponse {
        val account = accountsGateway.findByLogin(request.login) ?:
                return GetAccountInfoResponse.Failed("User with login ${request.login} is not found")

        return GetAccountInfoResponse.Success(account.login, account.name, account.email)
    }
}