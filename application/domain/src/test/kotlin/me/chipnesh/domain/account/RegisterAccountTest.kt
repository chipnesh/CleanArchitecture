package me.chipnesh.domain.account

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Account
import me.chipnesh.domain.Result
import me.chipnesh.domain.notification.Notifications
import me.chipnesh.domain.notification.SendNotification
import me.chipnesh.domain.template.GetEmailTemplate
import me.chipnesh.domain.template.Templates
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@DisplayName("Test 'register account' use case")
@RunWith(JUnitPlatform::class)
class RegisterAccountTest {
    val accountsGateway = mock<Accounts>()
    val notificationGateway = mock<Notifications>()
    val templateGateway = mock<Templates>()
    val registerAccount = RegisterAccount(
            accountsGateway,
            ValidateAccountRegistrationRequest(),
            SendRegisteredNotification(
                    SendNotification(notificationGateway, GetEmailTemplate(templateGateway))
            )
    )

    @Test
    @DisplayName("Should return success with account id")
    suspend fun shouldRegisterAccount() {
        val expectedAccount = givenAnAccountIsFound()

        val response = registerAccount.execute(RegisterAccountRequest(
                expectedAccount.login,
                expectedAccount.name,
                expectedAccount.email,
                "123456"
        )) as Result.Success

        assertThat(response.result.id, equalTo(expectedAccount.id))
        assertThat(response.success, equalTo(true))
    }

    @Test
    @DisplayName("Should return message with incorrect fields")
    suspend fun shouldReturnIncorrectFormMessage() {
        val response = registerAccount.execute(RegisterAccountRequest("1", "", "", "123456")) as
                Result.Failed
        assertThat(response.success, equalTo(false))
        assertThat(response.messages,
                hasElement("Name is empty")
                and
                hasElement("Email is empty")
        )
    }

    private suspend fun givenAnAccountIsFound(): Account {
        val account = Account("1", "login", "name", "login@mail.ru", "1")
        whenever(accountsGateway.findByLogin("login")).thenReturn(account)
        whenever(accountsGateway.save(any())).thenReturn(account)
        return account
    }
}