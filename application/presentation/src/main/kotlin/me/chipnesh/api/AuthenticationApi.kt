package me.chipnesh.api

import me.chipnesh.presentation.wrappers.axios.axios
import me.chipnesh.presentation.wrappers.axios.execute
import me.chipnesh.presentation.wrappers.axios.post
import kotlin.coroutines.experimental.suspendCoroutine

actual class AuthenticationApi {
    actual suspend fun authenticate(form: AuthenticationForm): AuthenticationResult = suspendCoroutine { c ->
        axios<AuthenticationResult>(post("/account", {
            data = JSON.stringify(form)
        })).execute(c)
    }

    actual suspend fun isAuthenticated(sessionId: String): IsAuthenticatedResult = suspendCoroutine { c ->
        axios<IsAuthenticatedResult>(post("/account", {
            params["sessionId"] = sessionId
        })).execute(c)
    }
}