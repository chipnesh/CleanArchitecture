package me.chipnesh.presentation

import me.chipnesh.api.AccountApi
import me.chipnesh.api.AccountInfoResult
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.wrappers.async.async
import me.chipnesh.presentation.wrappers.react.redux.thunk

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

fun User.UserReducer(action: Action) = when (action) {
    is Action.GetUser -> {
        val result = action.result
        when (result) {
            is AccountInfoResult.Success -> copy(result.login, result.login, result.email)
            is AccountInfoResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> this
}

fun getUser(login: String) {
    store.dispatch {
        thunk<State> {
            async {
                accounts.info(login)
            }.then { result ->
                dispatch(Action.GetUser(result))
            }
        }
    }
}