package me.chipnesh.configuration

import me.chipnesh.api.AccountApi
import me.chipnesh.api.AuthenticationApi
import me.chipnesh.api.QuoteApi
import me.chipnesh.domain.account.GetAccountInfo
import me.chipnesh.domain.account.RegisterAccount
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.quote.GetChuckNorrisQuote
import me.chipnesh.api.ExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Rest {
    @Bean
    open fun accountApi(registerAccount: RegisterAccount, getAccountInfo: GetAccountInfo)
            = AccountApi(registerAccount, getAccountInfo)

    @Bean
    open fun authenticationApi(authenticateUser: AuthenticateUser, isAuthenticated: IsAuthenticated)
            = AuthenticationApi(authenticateUser, isAuthenticated)

    @Bean
    open fun quoteApi(getChuckNorrisQuote: GetChuckNorrisQuote)
            = QuoteApi(getChuckNorrisQuote)

    @Bean
    open fun exceptionHandler() = ExceptionHandler()
}