package me.chipnesh.presentation

import me.chipnesh.api.AccountApi
import me.chipnesh.api.AccountInfoResult
import me.chipnesh.presentation.components.Action

val accounts = AccountApi()

data class User(
        val login: String,
        val name: String,
        val email: String
) {
    companion object {
        val ANON = User("anonymous", "anonymous", "anonymous@mail.com")
    }
    fun isAnon() = this == ANON
}

suspend fun User.UserReducer(action: Action) = when(action) {
    is Action.GetUser -> {
        val result = accounts.info(action.login)
        when (result) {
            is AccountInfoResult.Success -> copy(result.login, result.login, result.email)
            is AccountInfoResult.Failed -> throw IllegalArgumentException(result.messages.joinToString())
        }
    }
    else -> this
}