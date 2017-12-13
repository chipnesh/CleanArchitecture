package me.chipnesh.domain.authentication

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assertThat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Account
import me.chipnesh.domain.Result
import me.chipnesh.domain.Session
import me.chipnesh.domain.account.Accounts
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalDateTime

@DisplayName("Test 'authenticate user' use case")
@RunWith(JUnitPlatform::class)
class AuthenticateUserTest {

    val sessionGateway = mock<Sessions>()
    val accountsGateway = mock<Accounts>()
    val authenticateUser = AuthenticateUser(sessionGateway, accountsGateway, ValidateAuthenticationUserRequest())

    @Test
    @DisplayName("Should return success with session id")
    suspend fun shouldAuthenticateUser() {
        givenAnAccountIsFound()
        whenever(sessionGateway.save(any()))
                .thenReturn(Session("1", "login", true, LocalDateTime.now()))

        val response = authenticateUser.execute(
                AuthenticateUserRequest("login", "password")
        ) as Result.Success

        assertThat(response.success, equalTo(true))
        assertThat(response.result.id, equalTo("1"))
    }

    @Test
    @DisplayName("Should return failed with incorrect fields message")
    suspend fun shouldReturnMessageWithIncorrectFields() {
        val response = authenticateUser.execute(
                AuthenticateUserRequest("", "43")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.messages,
                hasElement("Login is empty")
                and
                hasElement("Password should contain at least 6 chars"))
    }

    @Test
    @DisplayName("Should return failed with 'Account ... not found' message")
    suspend fun shouldReturnAccountNotFound() {
        val response = authenticateUser.execute(
                AuthenticateUserRequest("johndoe", "123456")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.messages, hasElement("Account with login 'johndoe' not found"))
    }

    @Test
    @DisplayName("Should return failed with 'Wrong password' message")
    suspend fun shouldReturnWrongPassword() {
        givenAnAccountIsFound()

        val response = authenticateUser.execute(
                AuthenticateUserRequest("login", "wrongpassword")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.messages, hasElement("Wrong password"))
    }

    @Test
    @DisplayName("Should return success with renewed session id")
    suspend fun shouldRenewSessionIfExist() {
        givenAnAccountIsFound()
        givenSessionExists()
        whenever(sessionGateway.save(any()))
                .thenReturn(Session("2", "login", true, LocalDateTime.now()))

        val response = authenticateUser.execute(
                AuthenticateUserRequest("login", "password")
        ) as Result.Success

        assertThat(response.success, equalTo(true))
        assertThat(response.result.id, equalTo("2"))
    }

    private suspend fun givenSessionExists() {
        val session = Session("1", "login", true, LocalDateTime.now())
        whenever(sessionGateway.findActiveById("1")).thenReturn(session)
    }

    private suspend fun givenAnAccountIsFound(): Account {
        val account = Account("1", "login", "name", "login@mail.ru", "5f4dcc3b5aa765d61d8327deb882cf99")
        whenever(accountsGateway.findByLogin("login")).thenReturn(account)
        return account
    }
}