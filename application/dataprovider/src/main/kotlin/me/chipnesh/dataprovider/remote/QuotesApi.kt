package me.chipnesh.dataprovider.remote

import me.chipnesh.domain.Quote
import me.chipnesh.domain.quote.Quotes

data class QuoteItem(
        var icon_url: String = "",
        var id: String = "",
        var url: String = "",
        var value: String = ""
)

data class QuoteResponse(
        var total: Int = 0,
        var result: Array<QuoteItem>?
)

class QuotesApi : Quotes {

    suspend override fun findByWord(word: String): Quote? {
        val body = Request.create("https://api.chucknorris.io/jokes/search?query=$word")
                .get()
                .retrieve()
                .body<QuoteResponse>()

        return body?.result?.let {
            val quoteItem = it.randomItem()
            Quote(quoteItem.value)
        }
    }
}