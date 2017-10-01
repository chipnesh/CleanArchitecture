package me.chipnesh.dataprovider.db

import me.chipnesh.domain.Account
import me.chipnesh.domain.account.AccountsGateway
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.PreparedStatement
import java.sql.ResultSet

class AccountsRepository(private val jdbcTemplate: JdbcTemplate) : AccountsGateway {

    override fun add(account: Account): String {
        jdbcTemplate.update("insert into t_account (id, login, name, email, hash) values (?, ?, ?, ?, ?)",
                { ps: PreparedStatement ->
                    ps.setString(1, account.id)
                    ps.setString(2, account.login)
                    ps.setString(3, account.name)
                    ps.setString(4, account.email)
                    ps.setString(5, account.passwordHash)
                })
        return account.id
    }

    override fun findByLogin(login: String): Account? {
        return try {
            finByLoginOrThrow(login)
        } catch (e: IncorrectResultSizeDataAccessException) { null }
    }

    private fun finByLoginOrThrow(login: String): Account? {
        return jdbcTemplate.queryForObject(
                "select * from t_account where login = ?",
                arrayOf(login),
                { rs: ResultSet, _: Int ->
                    Account(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    )
                }
        )
    }
}