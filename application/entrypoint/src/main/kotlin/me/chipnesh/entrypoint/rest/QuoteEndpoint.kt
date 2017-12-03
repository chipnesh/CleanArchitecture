package me.chipnesh.entrypoint.rest

import me.chipnesh.domain.Result
import me.chipnesh.domain.quote.GetChuckNorrisQuote
import me.chipnesh.domain.quote.GetChuckNorrisQuoteRequest
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/quote"], produces = [APPLICATION_JSON_UTF8_VALUE])
class QuoteEndpoint(
        private val getChuckNorrisQuote: GetChuckNorrisQuote
) {
    @GetMapping
    @Cacheable
    suspend fun get(@RequestParam word: String): String {
        val result = getChuckNorrisQuote.execute(GetChuckNorrisQuoteRequest(word))
        return when (result) {
            is Result.Success -> result.result.quote
            is Result.Failed -> result.messages.joinToString(", ")
        }
    }
}