package me.chipnesh.domain.account

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Account
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class GetAccountInfoTest {

    val accountsGateway = mock<AccountsGateway>()
    val getAccountInfo = GetAccountInfo(accountsGateway)

    @Test
    @DisplayName("Should return success response with account info")
    fun shouldReturnAccountInfo() {
        val expectedAccount = givenAnAccountIsFound()

        val response = getAccountInfo.getInfo(GetAccountInfoRequest("1")) as GetAccountInfoResponse.Success

        assertThat(response.login, equalTo(expectedAccount.login))
        assertThat(response.email, equalTo(expectedAccount.email))
        assertThat(response.name, equalTo(expectedAccount.name))
    }

    @Test
    @DisplayName("Should return failed response if not found")
    fun shouldReturnNotFoundMessage() {
        givenAnAccountIsNotFound()

        val response = getAccountInfo.getInfo(GetAccountInfoRequest("1")) as GetAccountInfoResponse.Failed

        assertThat(response.message, equalTo("User with login 1 is not found"))
    }

    private fun givenAnAccountIsFound(): Account {
        val account = Account("1", "login", "name", "login@mail.ru", "1")
        whenever(accountsGateway.findByLogin("1")).thenReturn(account)
        return account
    }

    private fun givenAnAccountIsNotFound() {
        whenever(accountsGateway.findByLogin("1")).thenReturn(null)
    }
}