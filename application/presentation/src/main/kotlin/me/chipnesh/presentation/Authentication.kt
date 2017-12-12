package me.chipnesh.presentation

import me.chipnesh.api.AuthenticationApi
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.presentation.components.Action

val sessions = AuthenticationApi()

data class Session(
        val sessionId: String
) {
    companion object {
        val EMPTY = Session("")
    }
    fun isEmpty() = this == EMPTY
}

fun SessionReducer(session: Session, action: Action) : Session = when (action) {
    is Action.Login -> {
        val result = action.result
        when (result) {
            is AuthenticationResult.Success -> session.copy(result.sessionId)
            is AuthenticationResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> session
}