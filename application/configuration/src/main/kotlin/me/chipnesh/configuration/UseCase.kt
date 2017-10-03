package me.chipnesh.configuration

import me.chipnesh.dataprovider.file.TemplateStorage
import me.chipnesh.domain.account.*
import me.chipnesh.domain.authentication.AuthenticateUser
import me.chipnesh.domain.authentication.IsAuthenticated
import me.chipnesh.domain.authentication.SessionsGateway
import me.chipnesh.domain.authentication.ValidateAuthenticateUserRequest
import me.chipnesh.domain.notification.NotificationGateway
import me.chipnesh.domain.notification.SendNotification
import me.chipnesh.domain.template.GetEmailTemplate
import me.chipnesh.domain.template.TemplateGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UseCase {
    @Bean
    open fun getAccountInfo(accountsGateway: AccountsGateway)
            = GetAccountInfo(accountsGateway)

    @Bean
    open fun registerAccount(accountsGateway: AccountsGateway,
                             validation: ValidateRegisterAccountRequest,
                             sendRegisteredNotification: SendRegisteredNotification) =
            RegisterAccount(accountsGateway, validation, sendRegisteredNotification)

    @Bean
    open fun authenticateUser(sessionsGateway: SessionsGateway,
                              accountsGateway: AccountsGateway,
                              validation: ValidateAuthenticateUserRequest) =
            AuthenticateUser(sessionsGateway, accountsGateway, validation)

    @Bean
    open fun isAuthenticated(sessionsGateway: SessionsGateway)
            = IsAuthenticated(sessionsGateway)

    @Bean
    open fun sendRegisteredNotification(sendNotification: SendNotification)
            = SendRegisteredNotification(sendNotification)
    @Bean
    open fun sendNotification(getEmailTemplate: GetEmailTemplate,
                              notificationGateway: NotificationGateway)
            = SendNotification(notificationGateway, getEmailTemplate)

    @Bean
    open fun getEmailTemplate(templateGateway: TemplateGateway)
            = GetEmailTemplate(templateGateway)
}