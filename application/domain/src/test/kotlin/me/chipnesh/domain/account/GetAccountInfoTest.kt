package me.chipnesh.domain.account

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Account
import me.chipnesh.domain.Result
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class GetAccountInfoTest {

    val accountsGateway = mock<Accounts>()
    val getAccountInfo = GetAccountInfo(accountsGateway)

    @Test
    @DisplayName("Should return success response with account info")
    suspend fun shouldReturnAccountInfo() {
        val expectedAccount = givenAnAccountIsFound()

        val response = getAccountInfo.execute(GetAccountInfoRequest("1")) as Result.Success

        assertThat(response.result.login, equalTo(expectedAccount.login))
        assertThat(response.result.email, equalTo(expectedAccount.email))
        assertThat(response.result.name, equalTo(expectedAccount.name))
    }

    @Test
    @DisplayName("Should return failed response if not found")
    suspend fun shouldReturnNotFoundMessage() {
        givenAnAccountIsNotFound()

        val response = getAccountInfo.execute(GetAccountInfoRequest("1")) as Result.Failed

        assertThat(response.messages, hasElement("User with login 1 is not found"))
    }

    private suspend fun givenAnAccountIsFound(): Account {
        val account = Account("1", "login", "name", "login@mail.ru", "1")
        whenever(accountsGateway.findByLogin("1")).thenReturn(account)
        return account
    }

    private suspend fun givenAnAccountIsNotFound() {
        whenever(accountsGateway.findByLogin("1")).thenReturn(null)
    }
}