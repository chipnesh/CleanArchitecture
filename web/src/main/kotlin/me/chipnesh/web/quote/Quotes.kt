package me.chipnesh.web.quote

import me.chipnesh.api.QuoteApi
import me.chipnesh.web.State
import me.chipnesh.web.index.Action
import me.chipnesh.web.wrappers.js.async
import me.chipnesh.web.wrappers.redux.thunk

val quotes = QuoteApi()

fun quoteReducer(quota: String = "", action: Action) = when (action) {
    is Action.GetQuota -> action.quota
    else -> quota
}

fun getRandomQuote(keyword: String) = thunk<State> {
    async { quotes.get(keyword) }.then { quote ->
        dispatch(Action.GetQuota(quote))
    }
}