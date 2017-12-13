package me.chipnesh.api

import me.chipnesh.web.wrappers.axios.request
import me.chipnesh.web.wrappers.axios.execute
import me.chipnesh.web.wrappers.axios.get
import me.chipnesh.web.wrappers.axios.post

actual class AccountApi {
    actual suspend fun info(login: String) = request<AccountInfoResult>(
            get("/account", {
                params["login"] = login
            })
    ).execute()

    actual suspend fun register(form: RegistrationForm) = request<RegistrationResult>(
            post("/account", form)
    ).execute()
}