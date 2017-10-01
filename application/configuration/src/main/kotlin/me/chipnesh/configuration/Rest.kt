package me.chipnesh.configuration

import me.chipnesh.domain.account.GetAccountInfo
import me.chipnesh.domain.account.RegisterAccount
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.gateway.rest.AccountController
import me.chipnesh.gateway.rest.AuthenticationController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Rest {
    @Bean
    open fun accountController(registerAccount: RegisterAccount, getAccountInfo: GetAccountInfo)
            = AccountController(registerAccount, getAccountInfo)

    @Bean
    open fun authenticationController(authenticateUser: AuthenticateUser, isAuthenticated: IsAuthenticated)
            = AuthenticationController(authenticateUser, isAuthenticated)
}