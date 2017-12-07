package me.chipnesh.api

import me.chipnesh.presentation.wrappers.axios.axios
import me.chipnesh.presentation.wrappers.axios.execute
import me.chipnesh.presentation.wrappers.axios.get
import kotlin.coroutines.experimental.suspendCoroutine

actual class QuoteApi {
    actual suspend fun get(word: String): String = suspendCoroutine { c ->
        axios<String>(get("/quote", {
            params["word"] = word
        })).execute(c)
    }
}