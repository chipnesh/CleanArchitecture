package me.chipnesh.presentation

import me.chipnesh.api.QuoteApi
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.wrappers.js.async
import me.chipnesh.presentation.wrappers.redux.thunk

val quotes = QuoteApi()

fun quoteReducer(quota: String = "", action: Action) = when (action) {
    is Action.GetQuote -> action.quote
    else -> quota
}

fun getRandomQuote(keyword: String) = thunk<State> {
    async { quotes.get(keyword) }.then { quote ->
        dispatch(Action.GetQuote(quote))
    }
}