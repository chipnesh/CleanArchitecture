package me.chipnesh.configuration

import me.chipnesh.dataprovider.db.AccountsRepository
import me.chipnesh.dataprovider.db.SessionsRepository
import org.h2.jdbcx.JdbcConnectionPool
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
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
}