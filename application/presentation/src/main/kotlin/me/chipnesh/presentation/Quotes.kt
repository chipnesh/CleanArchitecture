package me.chipnesh.presentation

import me.chipnesh.api.QuoteApi
import me.chipnesh.presentation.components.Action

val quotes = QuoteApi()

suspend fun String.QuoteReducer(action: Action) = when(action) {
    is Action.GetQuote -> quotes.get(action.word)
    is Action.QuoteLoaded -> action.quote
    else -> this
}
