package me.chipnesh.domain.authentication

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.chipnesh.domain.Result
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
    suspend fun shouldReturnSessionId() {
        givenSessionExist(LocalDateTime.now().plusHours(1))

        val response = isAuthenticated.execute(
                IsAuthenticatedRequest("1")
        ) as Result.Success

        assertThat(response.success, equalTo(true))
        assertThat(response.result.id, equalTo("1"))
    }

    @Test
    @DisplayName("Should return failed if session doesn't exist")
    suspend fun shouldReturnNotAuthenticated() {
        val response = isAuthenticated.execute(
                IsAuthenticatedRequest("login")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.messages, hasElement("Not authenticated"))
    }

    @Test
    @DisplayName("Should return failed if session has expired")
    suspend fun shouldReturnSessionExpired() {
        givenSessionExist(LocalDateTime.now().minusHours(1))

        val response = isAuthenticated.execute(
                IsAuthenticatedRequest("1")
        ) as Result.Failed

        assertThat(response.success, equalTo(false))
        assertThat(response.messages, hasElement("Session expired"))
    }

    private fun givenSessionExist(expirationTime: LocalDateTime) {
        val session = Session("1", "login", true, expirationTime)
        whenever(sessionGateway.findActiveBySessionId("1")).thenReturn(session)
    }
}