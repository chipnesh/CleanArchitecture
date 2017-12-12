package me.chipnesh.presentation

import me.chipnesh.api.QuoteApi
import me.chipnesh.presentation.components.Action

val quotes = QuoteApi()

fun QuoteReducer(quote: String, action: Action) = when (action) {
    is Action.GetQuote -> quote // async quotes.get(action.quote)
    else -> quote
}