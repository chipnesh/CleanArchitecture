package me.chipnesh.presentation

import me.chipnesh.api.AccountApi
import me.chipnesh.api.AccountInfoResult
import me.chipnesh.presentation.User.Companion.ANON
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.wrappers.js.async
import me.chipnesh.presentation.wrappers.redux.thunk

val accounts = AccountApi()

data class User(
        val login: String,
        val name: String,
        val email: String
) {
    companion object {
        val ANON = User("", "anonymous", "")
    }

    fun isAnon() = this == ANON
}

fun userReducer(user: User = ANON, action: Action) = when (action) {
    is Action.GetUser -> {
        val result = action.result
        when (result) {
            is AccountInfoResult.Success -> user.copy(result.login, result.login, result.email)
            is AccountInfoResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> user
}

fun getUser(login: String) = thunk<State> {
    async { accounts.info(login) }.then { result ->
        dispatch(Action.GetUser(result))
    }
}