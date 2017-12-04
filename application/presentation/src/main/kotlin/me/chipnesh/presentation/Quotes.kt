package me.chipnesh.presentation

import me.chipnesh.api.QuoteApi
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.wrappers.async.async
import me.chipnesh.presentation.wrappers.redux.thunk

val quotes = QuoteApi()

fun String.QuoteReducer(action: Action) = when (action) {
    is Action.GetQuote -> action.quote
    else -> this
}

fun getQuote(word: String) {
    store.dispatch(thunk<State> {
        async({
            quotes.get(word)
        }).then { quote ->
            dispatch(Action.GetQuote(quote))
        }
    })
}