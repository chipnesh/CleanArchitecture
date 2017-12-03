package me.chipnesh.configuration

import me.chipnesh.dataprovider.file.TemplateStorage
import me.chipnesh.dataprovider.remote.QuotesApi
import me.chipnesh.dataprovider.remote.notification.ConsoleSender
import me.chipnesh.dataprovider.remote.notification.EmailSender
import me.chipnesh.dataprovider.remote.NotificationGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableCoroutineMongoRepositories
import org.springframework.kotlin.experimental.coroutine.event.CoroutineApplicationEventPublisher
import org.springframework.mail.javamail.JavaMailSender
import org.thymeleaf.spring5.SpringTemplateEngine

@Configuration
@EnableCoroutineMongoRepositories(basePackages = ["me.chipnesh.dataprovider.db"])
open class DataProviders(private val properties: ApplicationProperties) {

    @Bean
    open fun notificationSender(mailSender: JavaMailSender) = when {
        properties.useEmail -> EmailSender(mailSender)
        else -> ConsoleSender()
    }

    @Bean
    open fun notificationGateway(publisher: CoroutineApplicationEventPublisher)
            = NotificationGateway(publisher)

    @Bean
    open fun templateStorage(emailTemplateEngine: SpringTemplateEngine)
            = TemplateStorage(emailTemplateEngine)

    @Bean
    open fun quotes()
            = QuotesApi()
}