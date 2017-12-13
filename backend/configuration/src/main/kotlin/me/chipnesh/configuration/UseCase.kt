package me.chipnesh.configuration

import me.chipnesh.domain.account.*
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.authentication.Sessions
import me.chipnesh.domain.authentication.ValidateAuthenticationUserRequest
import me.chipnesh.domain.notification.Notifications
import me.chipnesh.domain.notification.SendNotification
import me.chipnesh.domain.quote.GetChuckNorrisQuote
import me.chipnesh.domain.quote.Quotes
import me.chipnesh.domain.template.GetEmailTemplate
import me.chipnesh.domain.template.Templates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UseCase {
    @Bean
    open fun getAccountInfo(accounts: Accounts)
            = GetAccountInfo(accounts)

    @Bean
    open fun registerAccount(accounts: Accounts,
                             validation: ValidateAccountRegistrationRequest,
                             sendRegisteredNotification: SendRegisteredNotification) =
            RegisterAccount(accounts, validation, sendRegisteredNotification)

    @Bean
    open fun authenticateUser(sessions: Sessions,
                              accounts: Accounts,
                              validation: ValidateAuthenticationUserRequest) =
            AuthenticateUser(sessions, accounts, validation)

    @Bean
    open fun isAuthenticated(sessions: Sessions)
            = IsAuthenticated(sessions)

    @Bean
    open fun sendRegisteredNotification(sendNotification: SendNotification)
            = SendRegisteredNotification(sendNotification)
    @Bean
    open fun sendNotification(getEmailTemplate: GetEmailTemplate,
                              notifications: Notifications)
            = SendNotification(notifications, getEmailTemplate)

    @Bean
    open fun getEmailTemplate(templates: Templates)
            = GetEmailTemplate(templates)

    @Bean
    open fun getChuckNorrisEndpoint(quotes: Quotes)
            = GetChuckNorrisQuote(quotes)
}