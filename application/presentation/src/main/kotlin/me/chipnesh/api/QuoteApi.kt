package me.chipnesh.api

import me.chipnesh.presentation.wrappers.axios.axios
import me.chipnesh.presentation.wrappers.axios.execute
import me.chipnesh.presentation.wrappers.axios.post
import kotlin.coroutines.experimental.suspendCoroutine

actual class QuoteApi {
    actual suspend fun get(word: String): String = suspendCoroutine { c ->
        axios<String>(post("/quote", {
            params["word"] = word
        })).execute(c)
    }
}