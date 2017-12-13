package me.chipnesh.api

import me.chipnesh.web.wrappers.axios.request
import me.chipnesh.web.wrappers.axios.execute
import me.chipnesh.web.wrappers.axios.get

actual class QuoteApi {
    actual suspend fun get(word: String) = request<String>(
            get("/quote", {
                params["word"] = word
            })
    ).execute()
}