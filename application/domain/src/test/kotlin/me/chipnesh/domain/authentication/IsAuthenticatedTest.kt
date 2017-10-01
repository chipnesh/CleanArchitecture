package me.chipnesh.domain.authentication

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Session
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalDateTime

@DisplayName("Test 'is authenticated' use case")
@RunWith(JUnitPlatform::class)
class IsAuthenticatedTest {
    val sessionGateway = mock<SessionsGateway>()
    val isAuthenticated = IsAuthenticated(sessionGateway)

    @Test
    @DisplayName("Should return success if session exist")
    fun shouldReturnSessionId() {
        givenSessionExist(LocalDateTime.now().plusHours(1))

        val response = isAuthenticated.isAuthenticated(
                IsAuthenticatedRequest("login")
        ) as IsAuthenticatedResponse.Authenticated

        assertThat(response.success, equalTo(true))
        assertThat(response.id, equalTo("1"))
    }

    @Test
    @DisplayName("Should return failed if session doesn't exist")
    fun shouldReturnNotAuthenticated() {
        val response = isAuthenticated.isAuthenticated(
                IsAuthenticatedRequest("login")
        ) as IsAuthenticatedResponse.NotAuthenticated

        assertThat(response.success, equalTo(false))
        assertThat(response.message, equalTo("Session wasn't created"))
    }

    @Test
    @DisplayName("Should return failed if session has expired")
    fun shouldReturnSessionExpired() {
        givenSessionExist(LocalDateTime.now().minusHours(1))

        val response = isAuthenticated.isAuthenticated(
                IsAuthenticatedRequest("login")
        ) as IsAuthenticatedResponse.NotAuthenticated

        assertThat(response.success, equalTo(false))
        assertThat(response.message, equalTo("Session has expired"))
    }

    private fun givenSessionExist(expirationTime: LocalDateTime) {
        val session = Session("1", "login", true, expirationTime)
        whenever(sessionGateway.findActiveByLogin("login")).thenReturn(session)
    }
}