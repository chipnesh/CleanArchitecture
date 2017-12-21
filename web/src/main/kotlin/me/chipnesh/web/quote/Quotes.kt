package me.chipnesh.web.quote

import me.chipnesh.api.QuoteApi
import me.chipnesh.web.Action
import me.chipnesh.web.State
import me.chipnesh.web.quote.QuotaAction.GetQuote
import me.chipnesh.web.wrappers.js.async
import me.chipnesh.web.wrappers.redux.thunk

sealed class QuotaAction : Action {
    data class GetQuote(val quote: String) : QuotaAction()
}

val quotes = QuoteApi()

fun quoteReducer(quote: String = "", action: Action) = when (action) {
    is GetQuote -> action.quote
    else -> quote
}

fun getRandomQuote(keyword: String) = thunk<State> {
    async { quotes.get(keyword) }.then { quote ->
        execute(GetQuote(quote))
    }
}