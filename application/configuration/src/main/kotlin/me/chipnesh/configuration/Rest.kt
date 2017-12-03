package me.chipnesh.configuration

import me.chipnesh.domain.account.GetAccountInfo
import me.chipnesh.domain.account.RegisterAccount
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.quote.GetChuckNorrisQuote
import me.chipnesh.entrypoint.rest.AccountEndpoint
import me.chipnesh.entrypoint.rest.AuthenticationEndpoint
import me.chipnesh.entrypoint.rest.ExceptionHandler
import me.chipnesh.entrypoint.rest.QuoteEndpoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Rest {
    @Bean
    open fun accountEndpoint(registerAccount: RegisterAccount, getAccountInfo: GetAccountInfo)
            = AccountEndpoint(registerAccount, getAccountInfo)

    @Bean
    open fun authenticationEndpoint(authenticateUser: AuthenticateUser, isAuthenticated: IsAuthenticated)
            = AuthenticationEndpoint(authenticateUser, isAuthenticated)

    @Bean
    open fun quoteEndpoint(getChuckNorrisQuote: GetChuckNorrisQuote)
            = QuoteEndpoint(getChuckNorrisQuote)

    @Bean
    open fun exceptionHandler() = ExceptionHandler()
}