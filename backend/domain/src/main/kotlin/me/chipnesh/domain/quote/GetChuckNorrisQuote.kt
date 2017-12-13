package me.chipnesh.domain.quote

import me.chipnesh.domain.Quote
import me.chipnesh.domain.Result
import me.chipnesh.domain.UseCase

data class GetChuckNorrisQuoteRequest(val word: String)
data class GetChuckNorrisQuoteResponse(val quote: String)

class GetChuckNorrisQuote(
        private val quotes: Quotes
) : UseCase<GetChuckNorrisQuoteRequest, GetChuckNorrisQuoteResponse> {

    suspend override fun execute(request: GetChuckNorrisQuoteRequest): Result<GetChuckNorrisQuoteResponse> {
        val quote = quotes.findByWord(request.word)
        return when (quote) {
            is Quote -> Result.Success(GetChuckNorrisQuoteResponse(quote.text))
            else -> Result.Failed("Quote not found")
        }
    }
}