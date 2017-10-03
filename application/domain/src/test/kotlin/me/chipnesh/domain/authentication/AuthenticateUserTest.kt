package me.chipnesh.domain.authentication

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Account
import me.chipnesh.domain.Result
import me.chipnesh.domain.Session
import me.chipnesh.domain.account.AccountsGateway
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalDateTime

@DisplayName("Test 'authenticate user' use case")
@RunWith(JUnitPlatform::class)
class AuthenticateUserTest {

    val sessionGateway = mock<SessionsGateway>()
    val accountsGateway = mock<AccountsGateway>()
    val authenticateUser = AuthenticateUser(sessionGateway, accountsGateway, ValidateAuthenticateUserRequest())

    @Test
    @DisplayName("Should return success with session id")
    fun shouldAuthenticateUser() {
        givenAnAccountIsFound()
        whenever(sessionGateway.add(any())).thenReturn("1")

        val response = authenticateUser.execute(
                AuthenticateUserRequest("login", "password")
        ) as Result.Success

        assertThat(response.success, equalTo(true))
        assertThat(response.result.id, equalTo("1"))
    }

    @Test
    @DisplayName("Should return failed with incorrect fields message")
    fun shouldReturnMessageWithIncorrectFields() {
        val response = authenticateUser.execute(
                AuthenticateUserRequest("", "43")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.message, equalTo(arrayOf(
                "Login is empty",
                "Password should contain at least 6 chars"
        ).joinToString()))
    }

    @Test
    @DisplayName("Should return failed with 'Account ... not found' message")
    fun shouldReturnAccountNotFound() {
        val response = authenticateUser.execute(
                AuthenticateUserRequest("johndoe", "123456")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.message, equalTo("Account with login 'johndoe' not found"))
    }

    @Test
    @DisplayName("Should return failed with 'Wrong password' message")
    fun shouldReturnWrongPassword() {
        givenAnAccountIsFound()

        val response = authenticateUser.execute(
                AuthenticateUserRequest("login", "wrongpassword")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.message, equalTo("Wrong password"))
    }

    @Test
    @DisplayName("Should return success with renewed session id")
    fun shouldRenewSessionIfExist() {
        givenAnAccountIsFound()
        givenSessionExists()
        whenever(sessionGateway.add(any())).thenReturn("2")

        val response = authenticateUser.execute(
                AuthenticateUserRequest("login", "password")
        ) as Result.Success

        assertThat(response.success, equalTo(true))
        assertThat(response.result.id, equalTo("2"))
    }

    private fun givenSessionExists() {
        val session = Session("1", "login", true, LocalDateTime.now())
        whenever(sessionGateway.findActiveByLogin("login")).thenReturn(session)
    }

    private fun givenAnAccountIsFound(): Account {
        val account = Account("1", "login", "name", "login@mail.ru", "5f4dcc3b5aa765d61d8327deb882cf99")
        whenever(accountsGateway.findByLogin("login")).thenReturn(account)
        return account
    }
}