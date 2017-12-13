package me.chipnesh.web.authentication

import me.chipnesh.api.AuthenticationApi
import me.chipnesh.api.AuthenticationForm
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.web.State
import me.chipnesh.web.authentication.Session.Companion.EMPTY
import me.chipnesh.web.index.Action
import me.chipnesh.web.wrappers.js.async
import me.chipnesh.web.wrappers.redux.thunk

val sessions = AuthenticationApi()

data class Session(
        val sessionId: String
) {
    companion object {
        val EMPTY = Session("")
    }
    fun isEmpty() = this == EMPTY
}

fun sessionReducer(session: Session = EMPTY, action: Action) = when (action) {
    is Action.Login -> {
        val result = action.result
        when (result) {
            is AuthenticationResult.Success -> session.copy(result.sessionId)
            is AuthenticationResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> session
}

fun login(login: String, password: String) = thunk<State> {
    async { sessions.authenticate(AuthenticationForm(login, password)) }.then { result ->
        dispatch(Action.Login(result))
    }
}