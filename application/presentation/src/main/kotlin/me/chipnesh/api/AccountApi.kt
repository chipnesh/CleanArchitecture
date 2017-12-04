package me.chipnesh.api

import kotlinx.serialization.json.JSON
import me.chipnesh.presentation.wrappers.axios.axios
import me.chipnesh.presentation.wrappers.axios.execute
import me.chipnesh.presentation.wrappers.axios.get
import me.chipnesh.presentation.wrappers.axios.post
import kotlin.coroutines.experimental.suspendCoroutine

actual class AccountApi {
    actual suspend fun info(login: String): AccountInfoResult = suspendCoroutine { c ->
        axios<AccountInfoResult>(get("/account", {
            params["login"] = login
        })).execute(c)
    }

    actual suspend fun register(form: RegistrationForm): RegistrationResult = suspendCoroutine { c ->
        axios<RegistrationResult>(post("/account", {
            data = JSON.stringify(form)
        })).execute(c)
    }
}