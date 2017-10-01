package me.chipnesh.domain.account

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Account
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@DisplayName("Test 'register account' use case")
@RunWith(JUnitPlatform::class)
class RegisterAccountTest {
    val accountsGateway = mock<AccountsGateway>()
    val registerAccount = RegisterAccount(accountsGateway, ValidateRegisterAccountRequest())

    @Test
    @DisplayName("Should return success with account id")
    fun shouldRegisterAccount() {
        val expectedAccount = givenAnAccountIsFound()

        val response = registerAccount.register(RegisterAccountRequest(
                expectedAccount.login,
                expectedAccount.name,
                expectedAccount.email,
                "123456"
        )) as RegisterAccountResponse.Success

        assertThat(response.id, equalTo(expectedAccount.id))
        assertThat(response.success, equalTo(true))
    }

    @Test
    @DisplayName("Should return message with incorrect fields")
    fun shouldReturnIncorrectFormMessage() {
        val response = registerAccount.register(RegisterAccountRequest("1", "", "", "123456")) as
                RegisterAccountResponse
        .Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.message, equalTo(arrayOf(
                "Name is empty",
                "Email is empty"
        ).joinToString()))
    }

    private fun givenAnAccountIsFound(): Account {
        val account = Account("1", "login", "name", "login@mail.ru", "1")
        whenever(accountsGateway.findByLogin("login")).thenReturn(account)
        whenever(accountsGateway.add(any())).thenReturn("1")
        return account
    }
}