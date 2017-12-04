package me.chipnesh.presentation

import me.chipnesh.api.AuthenticationApi
import me.chipnesh.api.AuthenticationForm
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.presentation.components.Action

val sessions = AuthenticationApi()

data class Session(
        val sessionId: String
) {
    companion object {
        val EMPTY = Session("EMPTY")
    }
    fun isEmpty() = this == EMPTY
}

suspend fun Session.SessionReducer(action: Action) = when(action) {
    is Action.Login -> {
        val result = sessions.authenticate(AuthenticationForm(action.login, action.password))
        when (result) {
            is AuthenticationResult.Success -> copy(result.sessionId)
            is AuthenticationResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> this
}
