package me.chipnesh.configuration

import me.chipnesh.domain.account.AccountsGateway
import me.chipnesh.domain.account.GetAccountInfo
import me.chipnesh.domain.account.RegisterAccount
import me.chipnesh.domain.account.ValidateRegisterAccountRequest
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.authentication.SessionsGateway
import me.chipnesh.domain.authentication.ValidateAuthenticateUserRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UseCase {
    @Bean
    open fun getAccountInfo(accountsGateway: AccountsGateway) = GetAccountInfo(accountsGateway)

    @Bean
    open fun registerAccount(accountsGateway: AccountsGateway,
                             validation: ValidateRegisterAccountRequest) =
            RegisterAccount(accountsGateway, validation)

    @Bean
    open fun authenticateUser(sessionsGateway: SessionsGateway,
                              accountsGateway: AccountsGateway,
                              validation: ValidateAuthenticateUserRequest) =
            AuthenticateUser(sessionsGateway, accountsGateway, validation)

    @Bean
    open fun isAuthenticated(sessionsGateway: SessionsGateway) = IsAuthenticated(sessionsGateway)
}