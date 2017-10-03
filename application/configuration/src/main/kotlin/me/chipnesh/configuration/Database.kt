package me.chipnesh.configuration

import me.chipnesh.dataprovider.db.AccountsRepository
import me.chipnesh.dataprovider.db.SessionsRepository
import me.chipnesh.dataprovider.file.TemplateStorage
import me.chipnesh.dataprovider.remote.EmailSender
import org.h2.jdbcx.JdbcConnectionPool
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.mail.javamail.JavaMailSender
import org.thymeleaf.spring5.SpringTemplateEngine
import javax.sql.DataSource

@Configuration
open class Database {

    @Bean
    open fun dataSource(): DataSource =
            JdbcConnectionPool.create("jdbc:h2:mem:example", "USER", "")

    @Bean
    open fun jdbcTemplate(datasource: DataSource) = JdbcTemplate(datasource)

    @Bean
    open fun transactionManager(dataSource: DataSource) = DataSourceTransactionManager(dataSource)

    @Bean
    open fun sessionsRepository(jdbcTemplate: JdbcTemplate) = SessionsRepository(jdbcTemplate)

    @Bean
    open fun accountsRepository(jdbcTemplate: JdbcTemplate) = AccountsRepository(jdbcTemplate)

    @Bean
    open fun emailSender(javaMailSender: JavaMailSender) = EmailSender(javaMailSender)

    @Bean
    open fun templateStorage(emailTemplateEngine: SpringTemplateEngine) = TemplateStorage(emailTemplateEngine)
}