package me.chipnesh.api

import me.chipnesh.web.wrappers.axios.request
import me.chipnesh.web.wrappers.axios.execute
import me.chipnesh.web.wrappers.axios.post

actual class AuthenticationApi {
    actual suspend fun authenticate(form: AuthenticationForm) = request<AuthenticationResult>(
            post("/account", form)
    ).execute()

    actual suspend fun isAuthenticated(sessionId: String) = request<IsAuthenticatedResult>(
            post("/account", config = {
                params["sessionId"] = sessionId
            })
    ).execute()
}