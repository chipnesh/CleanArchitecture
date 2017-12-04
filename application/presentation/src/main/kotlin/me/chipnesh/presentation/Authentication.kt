package me.chipnesh.presentation

import me.chipnesh.api.AuthenticationApi
import me.chipnesh.api.AuthenticationForm
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.wrappers.async.async
import me.chipnesh.presentation.wrappers.redux.thunk

val sessions = AuthenticationApi()

data class Session(
        val sessionId: String
) {
    companion object {
        val EMPTY = Session("")
    }
    fun isEmpty() = this == EMPTY
}

fun Session.SessionReducer(action: Action) = when (action) {
    is Action.Login -> {
        val result = action.result
        when (result) {
            is AuthenticationResult.Success -> copy(result.sessionId)
            is AuthenticationResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> this
}

fun login(login: String, password: String) {
    store.dispatch(thunk<State> {
        async {
            sessions.authenticate(AuthenticationForm(login, password))
        }.then { result ->
            dispatch(Action.Login(result))
        }
    })
}