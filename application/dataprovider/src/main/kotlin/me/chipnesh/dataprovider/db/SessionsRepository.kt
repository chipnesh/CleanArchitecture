package me.chipnesh.dataprovider.db

import me.chipnesh.domain.Session
import me.chipnesh.domain.authentication.SessionsGateway
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class SessionsRepository(private val jdbcTemplate: JdbcTemplate) : SessionsGateway {

    override fun add(session: Session): String {
        jdbcTemplate.update("INSERT INTO t_sessions (id, login, active, expirationDate) VALUES (?, ?, ?, ?)",
                { ps: PreparedStatement ->
                    ps.setString(1, session.id)
                    ps.setString(2, session.login)
                    ps.setString(3, session.active.toString())
                    ps.setString(4, session.expirationTime.format(ISO_LOCAL_DATE_TIME))
                })
        return session.id
    }

    override fun findActiveBySessionId(sessionId: String): Session? {
        return try {
            findActiveBy("id", sessionId)
        } catch (e: IncorrectResultSizeDataAccessException) {
            null
        }
    }

    override fun findActiveByLogin(login: String): Session? {
        return try {
            findActiveBy("login", login)
        } catch (e: IncorrectResultSizeDataAccessException) {
            null
        }
    }

    private fun findActiveBy(parameter: String, value: String): Session? {
        return jdbcTemplate.queryForObject("SELECT * FROM t_sessions WHERE $parameter = ? AND active = TRUE",
                arrayOf(value),
                { rs: ResultSet, _: Int ->
                    Session(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3).toBoolean(),
                            LocalDateTime.parse(rs.getString(4), ISO_LOCAL_DATE_TIME)
                    )
                }
        )
    }

    override fun remove(id: String) {
        jdbcTemplate.update("DELETE FROM t_sessions WHERE id = ?", id)
    }
}